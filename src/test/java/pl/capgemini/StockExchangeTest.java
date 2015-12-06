package pl.capgemini;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.gielda.StockExchange;
import pl.model.Stock;

public class StockExchangeTest {

	private BufferedReader fileReader;

	private StockExchange testClass;

	@Before
	public void setUp() throws IOException {
		fileReader = Mockito.mock(BufferedReader.class);
		Mockito.when(fileReader.readLine()).thenReturn("PKOBP,20130102,37.35");

		testClass = new StockExchange(fileReader);
	}

	@Test
	public void nextDayTest() throws IOException {
		// given
		Mockito.when(fileReader.readLine()).thenReturn("KGHM,20130102,193.1", "PGNIG,20130102,5.26",
				"JSW,20130102,94.6", "PKOBP,20130103,92.11", null);

		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(new Stock("PKOBP", 37.35, 0));
		expectedList.add(new Stock("KGHM", 193.1, 0));
		expectedList.add(new Stock("PGNIG", 5.26, 0));
		expectedList.add(new Stock("JSW", 94.6, 0));

		// when
		testClass.nextDay();
		List<Stock> actualList = testClass.currentDay();

		// then
		Assert.assertEquals(expectedList, actualList);
	}

	@Test
	public void hasNextDayTrue() throws IOException {
		// given
		Mockito.when(fileReader.readLine()).thenReturn("KGHM,20130102,193.1", "PGNIG,20130102,5.26",
				"JSW,20130102,94.6", "PKOBP,20130103,92.11", null);

		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(new Stock("PKOBP", 37.35, 0));
		expectedList.add(new Stock("KGHM", 193.1, 0));
		expectedList.add(new Stock("PGNIG", 5.26, 0));
		expectedList.add(new Stock("JSW", 94.6, 0));

		// when
		boolean hasNextDayFirst = testClass.hasNextDay();
		testClass.nextDay();
		boolean hasNextDaySecond = testClass.hasNextDay();

		// then
		Assert.assertTrue(hasNextDayFirst);
		Assert.assertTrue(hasNextDaySecond);
	}

	@Test
	public void hasNextDayFalse() throws IOException {
		// given
		Mockito.when(fileReader.readLine()).thenReturn("KGHM,20130102,193.1", "PGNIG,20130102,5.26",
				"JSW,20130102,94.6", null);

		List<Stock> expectedList = new ArrayList<Stock>();
		expectedList.add(new Stock("PKOBP", 37.35, 0));
		expectedList.add(new Stock("KGHM", 193.1, 0));
		expectedList.add(new Stock("PGNIG", 5.26, 0));
		expectedList.add(new Stock("JSW", 94.6, 0));

		// when
		boolean hasNextDayFirst = testClass.hasNextDay();
		testClass.nextDay();
		boolean hasNextDaySecond = testClass.hasNextDay();

		// then
		Assert.assertTrue(hasNextDayFirst);
		Assert.assertFalse(hasNextDaySecond);
	}

}
