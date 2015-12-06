package pl.capgemini;

import org.junit.Assert;
import org.junit.Test;

import pl.gracz.Strategy;
import pl.gracz.StrategyA;
import pl.model.Action;
import pl.model.Stock;

public class StrategyATest {
	Strategy strategyATest = new StrategyA();

	@Test
	public void yourStockIsNullTest() {
		// given
		Stock yourStock = null;
		Stock offeredStock = new Stock("PKOBP", 100.0, 0);

		// when
		Action result = strategyATest.action(yourStock, offeredStock);

		// then
		Assert.assertEquals(result, Action.BUY);
	}

	@Test
	public void yourStockIsCheaper() {
		// given
		Stock yourStock = new Stock("PKOBP", 70.0, 0);
		Stock offeredStock = new Stock("PKOBP", 100.0, 0);

		// when
		Action result = strategyATest.action(yourStock, offeredStock);

		// then
		Assert.assertEquals(result, Action.SELL);
	}

	@Test
	public void offeredStockIsCheaper() {
		// given
		Stock yourStock = new Stock("PKOBP", 100.0, 0);
		Stock offeredStock = new Stock("PKOBP", 70.0, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.BUY);
	}

	@Test
	public void offeredStockAndYourStockHaveTheSomePrice() {
		// given
		Stock yourStock = new Stock("PKOBP", 100.0, 0);
		Stock offeredStock = new Stock("PKOBP", 100.0, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.DO_NOTHING);
	}

	@Test
	public void offeredStockIsMaxDoubleAndYourStockMinValue() {
		// given
		Stock yourStock = new Stock("PKOBP", Double.MIN_VALUE, 0);
		Stock offeredStock = new Stock("PKOBP", Double.MAX_VALUE, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.SELL);
	}

	@Test
	public void offeredStockIsCheapertest1() {
		// given
		Stock yourStock = new Stock("PKOBP", 100.0, 0);
		Stock offeredStock = new Stock("PKOBP", 99.70, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.DO_NOTHING);
	}

	@Test
	public void offeredStockIsCheapertest2() {
		// given
		Stock yourStock = new Stock("PKOBP", 100.0, 0);
		Stock offeredStock = new Stock("PKOBP", 99.69, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.BUY);
	}

	@Test
	public void yourStockIsCheapertest1() {
		// given
		Stock yourStock = new Stock("PKOBP", 99.71, 0);
		Stock offeredStock = new Stock("PKOBP", 100.0, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.DO_NOTHING);
	}

	@Test
	public void yourStockIsCheapertest2() {
		// given
		Stock yourStock = new Stock("PKOBP", 99.70, 0);
		Stock offeredStock = new Stock("PKOBP", 100.0, 0);
		// when
		Action result = strategyATest.action(yourStock, offeredStock);
		// then
		Assert.assertEquals(result, Action.SELL);
	}

}
