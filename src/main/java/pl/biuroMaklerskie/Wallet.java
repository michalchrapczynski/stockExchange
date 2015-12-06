package pl.biuroMaklerskie;

import java.util.ArrayList;
import java.util.List;

import pl.model.Stock;

public class Wallet {

	private List<Stock> stocks = new ArrayList<Stock>();

	private Double currency;

	public Wallet(Double currency) {
		this.currency = currency;
	}

	public void deposit(Double currency) {
		this.currency += currency;
	}

	public void withdraw(Double currency) throws OutOfFoundsException {
		if (hasEnoughFunds(currency)) {
			this.currency -= currency;
		} else {
			throw new OutOfFoundsException("Not enough founds in wallet.");
		}
	}

	public boolean hasEnoughFunds(Double currency) {
		return this.currency >= currency;
	}

	public Double getCurrency() {
		return this.currency;
	}

	public void deposit(Stock stock) {
		stocks.add(stock);
	}

	public void withdraw(Stock stock) {
		stocks.remove(stock);
	}

	public List<Stock> peekStock(String name) {
		List<Stock> stocks = new ArrayList<Stock>();

		for (Stock stock : this.stocks) {
			if (stock.getName().equals(name)) {
				stocks.add(stock);
			}
		}

		return stocks;
	}

	public List<Stock> peekAllStocks() {
		return stocks;
	}

}
