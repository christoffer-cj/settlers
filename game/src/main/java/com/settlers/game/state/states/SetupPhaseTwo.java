package com.settlers.game.state.states;

import com.settlers.game.*;

import java.util.HashMap;
import java.util.Map;

public class SetupPhaseTwo extends BaseState {
    private final Map<Player, Boolean> hasPlacedBuilding;

    public SetupPhaseTwo(Game game) {
        super(game);
        hasPlacedBuilding = new HashMap<>();
        for (Player player : game.getPlayers()) {
            hasPlacedBuilding.put(player, false);
        }
    }

    @Override
    public boolean addBuilding(Position position, Building building) {
        if (hasPlacedBuilding.get(game.getCurrentPlayer())) return false;

        if (building.type() != Building.Type.SETTLEMENT) return false;

        if (!game.getBoard().addBuilding(position, building, true)) return false;

        hasPlacedBuilding.put(game.getCurrentPlayer(), true);

        return true;
    }

    @Override
    public boolean addRoad(Position position, Road road) {
        if (!hasPlacedBuilding.get(game.getCurrentPlayer())) return false;

        if (!game.getBoard().addRoad(position, road)) return false;

        if (!hasPlacedBuilding.values().stream().allMatch(x -> x)) {
            game.previousPlayer();
            return true;
        }

        game.setState(new RollForResources(game));

        return true;
    }
}
