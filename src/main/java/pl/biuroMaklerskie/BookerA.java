package pl.biuroMaklerskie;

import java.io.FileNotFoundException;
import java.util.List;

import pl.gielda.StockExchange;
import pl.gracz.Strategy;
import pl.model.Action;
import pl.model.Stock;

public class BookerA implements Booker {

	private static final double PROVISION = 0.005;

	private static final double TAX_BUY = 1 + PROVISION;
	private static final double TAX_SELL = 1 - PROVISION;

	private Wallet wallet;

	private StockExchange stockExchange;

	public BookerA(Double currency, StockExchange stockExchange) {
		wallet = new Wallet(currency);
		this.stockExchange = stockExchange;
	}

	@Override
	public void play(Strategy strategy) throws OutOfFoundsException, FileNotFoundException {
		List<Stock> currentStocks = stockExchange.currentDay();

		for (Stock currentStock : currentStocks) {
			String name = currentStock.getName();

			List<Stock> myStockts = wallet.peekStock(name);

			if (!myStockts.isEmpty()) {
				for (Stock myStock : myStockts) {
					doActionWhenOwningThisStock(strategy, currentStock, myStock);
				}
			} else {
				doActionWhenNotOwningThisStock(strategy, currentStock);
			}
		}
	}

	private void doActionWhenOwningThisStock(Strategy strategy, Stock currentStock, Stock myStock)
			throws OutOfFoundsException {
		Action action = strategy.action(myStock, currentStock);
		if (action == Action.BUY) {
			buyStock(currentStock, myStock, wallet.getCurrency());
		} else if (action == Action.SELL) {
			sellStock(myStock, currentStock.getPrice());
		}
	}

	private void buyStock(Stock stock, Stock myStock, Double priceLimit) throws OutOfFoundsException {
		Integer buyAmount = Integer.valueOf((int) (priceLimit / stock.getPrice()));
		if (!wallet.hasEnoughFunds(stock.getPrice() * buyAmount * TAX_BUY)) {
			buyAmount = Integer.valueOf((int) ((priceLimit * (1 / TAX_BUY) / stock.getPrice())));
		}
		myStock.addQuantity(buyAmount);
		wallet.withdraw(stock.getPrice() * buyAmount * TAX_BUY);
	}

	private void sellStock(Stock stock, Double sellPrice) {
		wallet.deposit(sellPrice * stock.getQuantity() * TAX_SELL);
		wallet.withdraw(stock);
	}

	private void doActionWhenNotOwningThisStock(Strategy strategy, Stock currentStock) throws OutOfFoundsException {
		Action action = strategy.action(null, currentStock);
		if (action == Action.BUY) {
			buyStock(currentStock, wallet.getCurrency());
		}
	}

	private void buyStock(Stock stock, Double priceLimit) throws OutOfFoundsException {
		Integer buyAmount = Integer.valueOf((int) (priceLimit / stock.getPrice()));
		if (!wallet.hasEnoughFunds(stock.getPrice() * buyAmount * TAX_BUY)) {
			buyAmount = Integer.valueOf((int) ((priceLimit * (1 / TAX_BUY) / stock.getPrice())));
		}
		stock.setQuantity(buyAmount);
		wallet.deposit(stock);
		wallet.withdraw(stock.getPrice() * buyAmount * TAX_BUY);
	}

	@Override
	public void deposit(Double amount) {
		wallet.deposit(amount);
	}

	@Override
	public void withdraw(Double amount) throws OutOfFoundsException {
		wallet.withdraw(amount);
	}

	public List<Stock> getListOfStocks() {
		return wallet.peekAllStocks();
	}

	@Override
	public void printEndScore() {
		Double total = wallet.getCurrency();
		System.out.printf("Money: %f\n", wallet.getCurrency());

		List<Stock> stocks = wallet.peekAllStocks();

		for (Stock stock : stocks) {
			double stockValue = stock.getPrice() * stock.getQuantity();
			System.out.printf("Stock %s: %f\n", stock.getName(), stockValue);
			total += stockValue;
		}

		System.out.printf("Total value: %f", total);
	}

}