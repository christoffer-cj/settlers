package com.settlers.game.states;

import com.settlers.game.*;

import java.util.Map;

public interface State {
    boolean rollDice(Player player);

    boolean addBuilding(Player player, Position position, Building building);

    boolean addRoad(Player player, Position position, Road road);

    boolean discardResources(Player player, Map<Resource, Integer> resources);

    boolean moveRobber(Player player, Coordinate coordinate);

    boolean offerTrade(Player player, Trade trade);
}
