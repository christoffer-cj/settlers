package com.settlers.game.state.states;

import com.settlers.game.Building;
import com.settlers.game.Game;
import com.settlers.game.Position;
import com.settlers.game.Road;
import com.settlers.game.state.State;

import java.util.Objects;

public abstract class BaseState implements State {
    protected final Game game;

    public BaseState(Game game) {
        this.game = Objects.requireNonNull(game);
    }

    @Override
    public boolean rollDice() {
        return false;
    }

    @Override
    public boolean addBuilding(Position position, Building building) {
        return false;
    }

    @Override
    public boolean addRoad(Position position, Road road) {
        return false;
    }
}
