package com.settlers.game.state.states;

import com.settlers.game.*;

import java.util.HashMap;
import java.util.Map;

public class SetupPhase extends BaseState {
    private final Map<Player, Boolean> hasPlacedBuilding;

    public SetupPhase(Game game) {
        super(game);
        hasPlacedBuilding = new HashMap<>();
        for (Player player : game.getPlayers()) {
            hasPlacedBuilding.put(player, false);
        }
    }

    @Override
    public boolean addBuilding(Position position, Building building) {
        if (hasPlacedBuilding.get(game.getCurrentPlayer())) {
            return false;
        }
        if (building.type() != Building.Type.SETTLEMENT) {
            return false;
        }

        return false; // todo
    }

    @Override
    public boolean addRoad(Position position, Road road) {
        return super.addRoad(position, road);
    }
}
