package com.settlers.game.states;

import com.settlers.game.*;

import java.util.Map;

public class TradingPhase extends BaseState {
    private Trade tradeInProgress;

    public TradingPhase(Game game) {
        super(game);
        tradeInProgress = null;
    }

    @Override
    public boolean offerTrade(Player player, Trade trade) {
        if (tradeInProgress != null) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!trade.offeringPlayer().equals(player)) return false;

        if (trade.offeringPlayer().equals(trade.receivingPlayer())) return false;

        if (!game.getPlayers().contains(trade.receivingPlayer())) return false;

        Inventory offeringPlayerInventory = game.getPlayer(trade.offeringPlayer().color()).inventory();
        for (Map.Entry<Resource, Integer> entry : trade.offer().entrySet()) {
            if (offeringPlayerInventory.resources().get(entry.getKey()) < entry.getValue()) return false;
        }
        Inventory receivingPlayerInventory = game.getPlayer(trade.receivingPlayer().color()).inventory();
        for (Map.Entry<Resource, Integer> entry : trade.receive().entrySet()) {
            if (receivingPlayerInventory.resources().get(entry.getKey()) < entry.getValue()) return false;
        }
        tradeInProgress = trade;

        return true;
    }

    @Override
    public boolean acceptTrade(Player player) {
        if (tradeInProgress == null) return false;
        if (!player.equals(tradeInProgress.receivingPlayer())) return false;

        Inventory offeringPlayerInventory = game.getPlayer(tradeInProgress.offeringPlayer().color()).inventory();
        Inventory receivingPlayerInventory = game.getPlayer(tradeInProgress.receivingPlayer().color()).inventory();
        for (Map.Entry<Resource, Integer> offerEntry : tradeInProgress.offer().entrySet()) {
            offeringPlayerInventory.resources().merge(offerEntry.getKey(), offerEntry.getValue(), (a, b) -> a - b);
            receivingPlayerInventory.resources().merge(offerEntry.getKey(), offerEntry.getValue(), Integer::sum);
        }
        for (Map.Entry<Resource, Integer> receiveEntry : tradeInProgress.receive().entrySet()) {
            receivingPlayerInventory.resources().merge(receiveEntry.getKey(), receiveEntry.getValue(), (a, b) -> a - b);
            offeringPlayerInventory.resources().merge(receiveEntry.getKey(), receiveEntry.getValue(), Integer::sum);
        }

        tradeInProgress = null;

        return true;
    }

    @Override
    public boolean declineTrade(Player player) {
        if (tradeInProgress == null) return false;
        if (!player.equals(tradeInProgress.receivingPlayer())) return false;

        tradeInProgress = null;

        return true;
    }

    @Override
    public boolean endTradingPhase(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (tradeInProgress != null) return false;
        game.setState(new BuildingPhase(game));

        return true;
    }
}
