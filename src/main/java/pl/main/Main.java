package pl.main;

import java.io.FileNotFoundException;

import pl.biuroMaklerskie.OutOfFoundsException;
import pl.gracz.Investor;

public class Main {

	public static void main(String[] args) throws OutOfFoundsException, FileNotFoundException {
		Investor inv = new Investor();
		inv.play();
	}

}
