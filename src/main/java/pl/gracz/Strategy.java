package pl.gracz;

import pl.model.Action;
import pl.model.Stock;

public interface Strategy {

	public Action action(Stock yourStock, Stock offeredStock);

}