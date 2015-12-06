package pl.gracz;

import pl.model.Action;
import pl.model.Stock;

public class StrategyA implements Strategy {

	@Override
	public Action action(Stock yourStock, Stock offeredStock) {
		if (yourStock == null) {
			return Action.BUY;
		} else if (getCurrentPriceDiff(yourStock, offeredStock) < -0.3) {
			return Action.BUY;
		} else if (getCurrentPriceDiff(yourStock, offeredStock) > 0.3) {
			return Action.SELL;
		}
		return Action.DO_NOTHING;
	}

	private double getCurrentPriceDiff(Stock yourStock, Stock offeredStock) {
		return ((offeredStock.getPrice() - yourStock.getPrice()) / yourStock.getPrice()) * 100;
	}

}
