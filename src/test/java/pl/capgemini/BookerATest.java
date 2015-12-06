package pl.capgemini;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.biuroMaklerskie.BookerA;
import pl.biuroMaklerskie.OutOfFoundsException;
import pl.gielda.StockExchange;
import pl.gracz.Strategy;
import pl.model.Action;
import pl.model.Stock;

public class BookerATest {

	private static final Double START_MONEY = 100.0;

	private BookerA testClass;

	private StockExchange stockExchange;

	private Strategy strategy;

	@Before
	public void setUp() {
		stockExchange = Mockito.mock(StockExchange.class);
		strategy = Mockito.mock(Strategy.class);

		testClass = new BookerA(START_MONEY, stockExchange);
	}

	@Test
	public void testCurrentDayIsEmpty() throws FileNotFoundException, OutOfFoundsException {
		// given
		Mockito.when(stockExchange.currentDay()).thenReturn(new ArrayList<Stock>());

		// when
		testClass.play(strategy);

		// then
		Assert.assertTrue(testClass.getListOfStocks().isEmpty());
	}

	@Test
	public void testNoItemsInWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);

		// when
		testClass.play(strategy);

		// then
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testBuyWhenItemsInWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("BBB", 1.0, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(null, f)).thenReturn(Action.BUY);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		value.add(e);
		value.add(f);
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testDoNothingWhenNotOwningWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("BBB", 10.0, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(null, f)).thenReturn(Action.DO_NOTHING);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		value.add(e);
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testBuyWhenSameItemInWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("AAA", 9.55, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(e, f)).thenReturn(Action.BUY);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		value.add(e);
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testBuyWhenSameItemInWallet2() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("AAA", 1.0, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(e, f)).thenReturn(Action.BUY);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		value.add(e);
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testSellWhenItemsInWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("AAA", 20.0, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(e, f)).thenReturn(Action.SELL);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testDoNothingWhenItemsInWallet() throws FileNotFoundException, OutOfFoundsException {
		// given
		List<Stock> value = new ArrayList<Stock>();
		Stock e = new Stock("AAA", 10.0, 9);
		value.add(e);
		Mockito.when(stockExchange.currentDay()).thenReturn(value);
		Mockito.when(strategy.action(null, e)).thenReturn(Action.BUY);
		testClass.play(strategy);

		Stock f = new Stock("AAA", 10.0, 9);
		value.clear();
		value.add(f);
		Mockito.when(strategy.action(e, f)).thenReturn(Action.DO_NOTHING);

		// when
		testClass.play(strategy);

		// then
		value.clear();
		value.add(e);
		Assert.assertEquals(value, testClass.getListOfStocks());
	}

	@Test
	public void testDeposit() throws OutOfFoundsException {
		// when
		testClass.deposit(200.0);
		testClass.withdraw(300.0);

		// then no exception it thrown
	}

	@Test(expected = OutOfFoundsException.class)
	public void testWithrowMoreMoneyThnWeHave() throws OutOfFoundsException {
		// when
		testClass.deposit(200.0);
		testClass.withdraw(300.01);
	}

}
