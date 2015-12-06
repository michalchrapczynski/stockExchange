package pl.capgemini;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import pl.biuroMaklerskie.OutOfFoundsException;
import pl.biuroMaklerskie.Wallet;
import pl.model.Stock;

public class WalletTest {

	@Test
	public void depositMoneyTest() {
		// given
		Wallet wallet = new Wallet(1000.0);

		// when
		wallet.deposit(200.0);
		Double result = wallet.getCurrency();

		// then
		Assert.assertEquals(1200.0, result.doubleValue(), 0);

	}

	@Test
	public void withdrawMoneyTest() {
		// given
		Wallet wallet = new Wallet(1000.0);

		// when
		try {
			wallet.withdraw(200.0);
		} catch (OutOfFoundsException e) {
			e.printStackTrace();
		}
		Double result = wallet.getCurrency();

		// then
		Assert.assertEquals(800.0, result.doubleValue(), 0);

	}

	@Test
	public void withdrawMoneyOutOfFoundsExceptionTest() {
		// given
		Wallet wallet = new Wallet(199.99);
		String msg = "Not enough founds in wallet.";

		// when
		try {
			wallet.withdraw(200.0);
			fail("OutOfFoundsException expected");
		} catch (OutOfFoundsException e) {
			// then
			Assert.assertEquals(msg, e.getMessage());
		}

	}

	@Test
	public void depositStock() {
		// given
		Wallet wallet = new Wallet(1000.0);
		Stock stock = new Stock("PKOBP", 35.55, 10);
		Stock stock2 = new Stock("KGHM", 100.55, 10);
		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(stock);
		expectedList.add(stock2);

		// when
		wallet.deposit(stock);
		wallet.deposit(stock2);
		List<Stock> actualList = wallet.peekAllStocks();

		// then
		Assert.assertEquals(expectedList, actualList);
	}

	@Test
	public void withdrawStock() {
		// given
		Wallet wallet = new Wallet(1000.0);
		Stock stock = new Stock("PKOBP", 35.55, 10);
		Stock stock2 = new Stock("KGHM", 100.55, 10);
		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(stock2);

		// when
		wallet.deposit(stock);
		wallet.deposit(stock2);
		wallet.withdraw(stock);
		List<Stock> actualList = wallet.peekAllStocks();

		// then
		Assert.assertEquals(expectedList, actualList);
	}

	@Test
	public void peekStock() {
		// given
		Wallet wallet = new Wallet(1000.0);
		Stock stockPKO1 = new Stock("PKOBP", 35.55, 10);
		Stock stockPKO2 = new Stock("PKOBP", 65.55, 10);
		Stock stockPKO3 = new Stock("PKOBP", 95.55, 10);
		Stock stock2KGHM1 = new Stock("KGHM", 100.55, 10);
		Stock stock2KGHM2 = new Stock("KGHM", 150.55, 10);
		Stock stock2KGHM3 = new Stock("KGHM", 200.55, 10);

		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(stockPKO1);
		expectedList.add(stockPKO2);
		expectedList.add(stockPKO3);
		expectedList.add(stock2KGHM1);
		expectedList.add(stock2KGHM2);
		expectedList.add(stock2KGHM3);

		// when
		wallet.deposit(stockPKO1);
		wallet.deposit(stockPKO2);
		wallet.deposit(stockPKO3);
		wallet.deposit(stock2KGHM1);
		wallet.deposit(stock2KGHM2);
		wallet.deposit(stock2KGHM3);
		List<Stock> actualTotalList = wallet.peekAllStocks();
		List<Stock> actualList = wallet.peekStock("PKOBP");

		// then

		Assert.assertEquals(6, actualTotalList.size());
		Assert.assertEquals(3, actualList.size());
	}

}
