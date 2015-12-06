package pl.biuroMaklerskie;

import java.io.FileNotFoundException;

import pl.gracz.Strategy;

public interface Booker {

	public void play(Strategy strategy) throws OutOfFoundsException, FileNotFoundException;

	public void deposit(Double amount);

	public void withdraw(Double amount) throws OutOfFoundsException;

	public void printEndScore();
}
