package com.settlers.game.states.impl;

import com.settlers.game.*;
import com.settlers.game.states.State;

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
}
