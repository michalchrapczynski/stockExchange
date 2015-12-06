package pl.gielda;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.model.Stock;

public class StockExchange {

	private static final String FILE_TO_PARSE = "src/resource/dane.csv";

	private static final String DELIMITER = ",";

	private BufferedReader fileReader;

	private String nextLine = "START";

	private String currentDate = "START";

	private List<Stock> stocks = new ArrayList<Stock>();

	public StockExchange() {
		try {
			fileReader = new BufferedReader(new FileReader(FILE_TO_PARSE));

			nextLine = fileReader.readLine();
			currentDate = nextLine.split(DELIMITER)[1];
		} catch (IOException e) {
			nextLine = "";
		}
	}

	/**
	 * for unit testing
	 */
	public StockExchange(BufferedReader fileReader) throws IOException {
		this.fileReader = fileReader;
		nextLine = fileReader.readLine();
		currentDate = nextLine.split(DELIMITER)[1];
	}

	public boolean hasNextDay() {
		return !("".equals(nextLine));
	}

	public void nextDay() {
		try {
			extractNewDay();
		} catch (IOException e) {
			nextLine = "";
		}
	}

	private void extractNewDay() throws IOException {
		stocks.clear();

		String date = "";
		do {
			stocks.add(extractStockFromCurrentLine());

			nextLine = fileReader.readLine();
			if (nextLine == null) {
				throw new EOFException("End of file");
			}
			date = nextLine.split(DELIMITER)[1];
		} while (date.equals(currentDate));

		currentDate = date;
	}

	private Stock extractStockFromCurrentLine() {
		String[] stockData = nextLine.split(DELIMITER);
		String nameCompany = stockData[0];
		Double price = Double.parseDouble(stockData[2]);
		return new Stock(nameCompany, price, 0);
	}

	public List<Stock> currentDay() {
		return stocks;
	}

}
