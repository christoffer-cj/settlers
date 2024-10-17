package com.settlers.game.states;

import com.settlers.game.*;

import java.util.List;
import java.util.Map;

public class TradingPhase extends BaseState {
    private Trade tradeInProgress;
    private boolean hasUsedDevelopmentCard;

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
    public boolean endTrading(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (tradeInProgress != null) return false;
        game.setState(new BuildingPhase(game, hasUsedDevelopmentCard));

        return true;
    }

    @Override
    public boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (hasUsedDevelopmentCard) return false;

        if ((game.getPlayer(player.color()).inventory().developmentCards().get(developmentCard) == 0)) return false;

        if (!List.of(DevelopmentCard.MONOPOLY, DevelopmentCard.KNIGHT,  DevelopmentCard.ROAD_BUILDING, DevelopmentCard.YEAR_OF_PLENTY).contains(developmentCard)) return false;

        game.getPlayer(player.color()).inventory().developmentCards().merge(developmentCard, -1, Integer::sum);

        switch (developmentCard) {
            case MONOPOLY -> game.setState(new Monopoly(game, this));
            case ROAD_BUILDING -> game.setState(new RoadBuilding(game, this));
            case YEAR_OF_PLENTY -> game.setState(new YearOfPlenty(game, this));
        }

        return true;
    }

    @Override
    public boolean exchange(Player player, Resource offer, Resource receive) {
        if (!game.getCurrentPlayer().equals(player)) return false;
        if (offer == Resource.NOTHING || receive == Resource.NOTHING) return false;

        int amountNeeded = 4;
        if (game.getBoard().hasHarbor(player, Harbor.ANY)) amountNeeded = 3;
        Harbor resourceHarbor = switch (offer) {
           case BRICK ->  Harbor.BRICK;
           case LUMBER ->  Harbor.LUMBER;
           case ORE ->  Harbor.ORE;
           case GRAIN ->  Harbor.GRAIN;
           case WOOL ->  Harbor.WOOL;
           default ->  Harbor.ANY; // dummy case to make compiler happy
       };
       if (game.getBoard().hasHarbor(player, resourceHarbor)) amountNeeded = 2;

       if (!(game.getPlayer(player.color()).inventory().resources().get(offer) >= amountNeeded)) return false;

       game.getPlayer(player.color()).inventory().resources().merge(receive, 1, Integer::sum);
       game.getPlayer(player.color()).inventory().resources().merge(offer, amountNeeded * -1, Integer::sum);

       return true;
    }
}
