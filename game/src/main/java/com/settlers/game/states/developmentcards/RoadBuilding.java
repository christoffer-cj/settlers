package com.settlers.game.states.developmentcards;

import com.settlers.game.Game;
import com.settlers.game.Player;
import com.settlers.game.Position;
import com.settlers.game.Road;
import com.settlers.game.states.BaseState;
import com.settlers.game.states.State;

import java.util.Objects;

public class RoadBuilding extends BaseState {
    private final State previousState;
    private boolean firstRoadAdded = false;

    public RoadBuilding(Game game, State previousState) {
        super(game);
        this.previousState = Objects.requireNonNull(previousState);
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        if (!game.getBoard().addRoad(position, road)) return false;

        if (firstRoadAdded) {
            game.setState(previousState);
            return true;
        } else {
            firstRoadAdded = true;
            return true;
        }
    }
}
