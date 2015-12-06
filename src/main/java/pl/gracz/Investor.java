package pl.gracz;

import java.io.FileNotFoundException;

import pl.biuroMaklerskie.Booker;
import pl.biuroMaklerskie.BookerA;
import pl.biuroMaklerskie.OutOfFoundsException;
import pl.gielda.StockExchange;

public class Investor {

	private static final Double START_MONEY = 10000.0;

	public void play() throws OutOfFoundsException, FileNotFoundException {
		StockExchange stockExchange = new StockExchange();
		Booker bookerA = new BookerA(START_MONEY, stockExchange);
		Strategy strategyA = new StrategyA();

		do {
			stockExchange.nextDay();
			bookerA.play(strategyA);

		} while (stockExchange.hasNextDay());

		bookerA.printEndScore();
	}

}
