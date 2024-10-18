package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;
import com.settlers.game.Position;
import com.settlers.game.Road;

import java.util.Objects;

public class RoadBuilding extends AbstractState {
    private final State previousState;
    private boolean firstRoadAdded = false;

    public RoadBuilding(Game game, State previousState) {
        super(game);
        this.previousState = Objects.requireNonNull(previousState);
        if (!game.getCurrentPlayer().inventory().hasRoads()) {
            game.setState(previousState);
        }
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        if (!game.getBoard().addRoad(position, road)) return false;
        game.getPlayer(player.color()).inventory().useRoad();

        if (firstRoadAdded) {
            game.setState(previousState);
            return true;
        } else {
            if (!game.getPlayer(player.color()).inventory().hasRoads()) {
                game.setState(previousState);
            }
            firstRoadAdded = true;
            return true;
        }
    }
}
