package com.settlers.game.states;

import com.settlers.game.*;

import java.util.Map;

public class ActionPhase extends AbstractState {
    private Trade tradeInProgress;
    private boolean hasUsedDevelopmentCard = false;

    public ActionPhase(Game game) {
        super(game);
    }

    @Override
    public boolean addBuilding(Player player, Position position, Building building) {
        if (tradeInProgress != null) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(building.color())) return false;

        if (!game.getPlayer(player.color()).canAffordBuilding(building.type())) return false;

        if (!game.getPlayer(player.color()).inventory().hasBuildings(building.type())) return false;

        if (!game.getBoard().addBuilding(position, building)) return false;
        game.getPlayer(player.color()).buyBuilding(building.type());

        return true;
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (tradeInProgress != null) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        if (!game.getPlayer(player.color()).canAffordRoad()) return false;

        if (!game.getPlayer(player.color()).inventory().hasRoads()) return false;

        if (!game.getBoard().addRoad(position, road)) return false;
        game.getPlayer(player.color()).buyRoad();

        return true;
    }

    @Override
    public boolean endTurn(Player player) {
        if (tradeInProgress != null) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        game.nextPlayer();
        game.setState(new RollForResources(game));

        return true;
    }

    @Override
    public boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard) {
        if (tradeInProgress != null) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (hasUsedDevelopmentCard) return false;

        if (!game.getPlayer(player.color()).inventory().useDevelopmentCard(developmentCard)) return false;

        switch (developmentCard) {
            case MONOPOLY -> game.setState(new Monopoly(game, this));
            case ROAD_BUILDING -> game.setState(new RoadBuilding(game, this));
            case YEAR_OF_PLENTY -> game.setState(new YearOfPlenty(game, this));
            case KNIGHT -> game.setState(new MoveRobber(game, this));
        }

        return true;
    }

    @Override
    public boolean exchange(Player player, Resource offer, Resource receive) {
        if (tradeInProgress != null) return false;

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
            default ->  null; // dummy case to make compiler happy
        };
        if (game.getBoard().hasHarbor(player, resourceHarbor)) amountNeeded = 2;

        if (!(game.getPlayer(player.color()).inventory().getResource(offer) >= amountNeeded)) return false;

        game.getPlayer(player.color()).inventory().putResource(receive, 1);
        game.getPlayer(player.color()).inventory().putResource(offer, amountNeeded * -1);

        return true;
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
            if (offeringPlayerInventory.getResource(entry.getKey()) < entry.getValue()) return false;
        }
        Inventory receivingPlayerInventory = game.getPlayer(trade.receivingPlayer().color()).inventory();
        for (Map.Entry<Resource, Integer> entry : trade.receive().entrySet()) {
            if (receivingPlayerInventory.getResource(entry.getKey()) < entry.getValue()) return false;
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
            offeringPlayerInventory.putResource(offerEntry.getKey(), offerEntry.getValue() * -1);
            receivingPlayerInventory.putResource(offerEntry.getKey(), offerEntry.getValue());
        }
        for (Map.Entry<Resource, Integer> receiveEntry : tradeInProgress.receive().entrySet()) {
            receivingPlayerInventory.putResource(receiveEntry.getKey(), receiveEntry.getValue() * -1);
            offeringPlayerInventory.putResource(receiveEntry.getKey(), receiveEntry.getValue());
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
}
