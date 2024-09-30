package com.settlers.game.states.impl;

import com.settlers.game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SetupPhase extends BaseState {
    private final Map<Player, Position> settlementPositions;
    private final boolean isFirstRound;

    public SetupPhase(Game game, boolean isFirstRound) {
        super(game);
        settlementPositions = new HashMap<>();
        for (Player player : game.getPlayers()) {
            settlementPositions.put(player, null);
        }

        this.isFirstRound = isFirstRound;
    }

    @Override
    public boolean addBuilding(Position position, Building building) {
        if (settlementPositions.get(game.getCurrentPlayer()) != null) return false;

        if (building.type() != Building.Type.SETTLEMENT) return false;

        if (!game.getBoard().addBuilding(position, building, true)) return false;

        settlementPositions.put(game.getCurrentPlayer(), position);

        return true;
    }

    @Override
    public boolean addRoad(Position position, Road road) {
        if (settlementPositions.get(game.getCurrentPlayer()) == null) return false;

        Position settlementPosition = settlementPositions.get(game.getCurrentPlayer());
        if (!settlementPosition.getAdjacentEdgesForVertex().contains(position)) return false;

        if (!game.getBoard().addRoad(position, road)) return false;

        if (!settlementPositions.values().stream().allMatch(Objects::nonNull)) {
            if (isFirstRound) {
                game.nextPlayer();
            } else {
                game.previousPlayer();
            }
            return true;
        }

        if (isFirstRound) {
            game.setState(new SetupPhase(game, false));
        } else {
            game.setState(new RollForResources(game));
        }

        return true;
    }
}