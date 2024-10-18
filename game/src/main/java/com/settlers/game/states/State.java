package com.settlers.game.states;

import com.settlers.game.*;

import java.util.Map;

public interface State {
    boolean rollDice(Player player);

    boolean addBuilding(Player player, Position position, Building building);

    boolean addRoad(Player player, Position position, Road road);

    boolean discardResources(Player player, Map<Resource, Integer> resources);

    boolean moveRobber(Player player, Coordinate coordinate);

    boolean stealResource(Player player, Player playerToStealFrom);

    boolean offerTrade(Player player, Trade trade);

    boolean acceptTrade(Player player);

    boolean declineTrade(Player player);

    boolean endTurn(Player player);

    boolean yearOfPlenty(Player player, Resource firstResource, Resource secondResource);

    boolean monopoly(Player player, Resource resource);

    boolean buyDevelopmentCard(Player player);

    boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard);

    boolean exchange(Player player, Resource offer, Resource receive);
}
