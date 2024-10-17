package com.settlers.game.states;

import com.settlers.game.*;

import java.util.Map;
import java.util.Objects;

public abstract class BaseState implements State {
    protected final Game game;

    public BaseState(Game game) {
        this.game = Objects.requireNonNull(game);
    }

    @Override
    public boolean rollDice(Player player) {
        return false;
    }

    @Override
    public boolean addBuilding(Player player, Position position, Building building) {
        return false;
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        return false;
    }

    @Override
    public boolean discardResources(Player player, Map<Resource, Integer> resources) {
        return false;
    }

    @Override
    public boolean moveRobber(Player player, Coordinate coordinate) {
        return false;
    }

    @Override
    public boolean offerTrade(Player player, Trade trade) {
        return false;
    }

    @Override
    public boolean acceptTrade(Player player) {
        return false;
    }

    @Override
    public boolean declineTrade(Player player) {
        return false;
    }

    @Override
    public boolean stealResource(Player player, Player playerToStealFrom) {
        return false;
    }

    @Override
    public boolean endTrading(Player player) {
        return false;
    }

    @Override
    public boolean endTurn(Player player) {
        return false;
    }

    @Override
    public boolean yearOfPlenty(Player player, Resource firstResource, Resource secondResource) {
        return false;
    }

    @Override
    public boolean monopoly(Player player, Resource resource) {
        return false;
    }

    @Override
    public boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard) {
       return false;
    }

    @Override
    public boolean exchange(Player player, Resource offer, Resource receive) {
        return false;
    }
}
