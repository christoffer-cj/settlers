package com.settlers.game.states;

import com.settlers.game.*;

import java.util.*;

public class SetupPhase extends AbstractState {
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
    public boolean addBuilding(Player player, Position position, Building building) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(building.color())) return false;

        if (settlementPositions.get(game.getCurrentPlayer()) != null) return false;

        if (building.type() != Building.Type.SETTLEMENT) return false;

        if (!game.getBoard().addBuilding(position, building, true)) return false;
        game.getPlayer(player.color()).inventory().useBuilding(building.type());

        settlementPositions.put(game.getCurrentPlayer(), position);

        return true;
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        if (settlementPositions.get(game.getCurrentPlayer()) == null) return false;

        Position settlementPosition = settlementPositions.get(game.getCurrentPlayer());
        if (!settlementPosition.getAdjacentEdgesForVertex().contains(position)) return false;

        if (!game.getBoard().addRoad(position, road)) return false;
        game.getPlayer(player.color()).inventory().useRoad();

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
            givePlayersStartingResources();
            game.setState(new RollForResources(game));
        }

        return true;
    }

    private void givePlayersStartingResources() {
        for (Map.Entry<Player, Position> entry : settlementPositions.entrySet()) {
            Collection<Resource> startingResources = entry.getValue()
                    .getAdjacentCoordinatesForVertex()
                    .stream()
                    .map(coordinate -> game.getBoard().getTile(coordinate))
                    .flatMap(Optional::stream)
                    .map(Tile::resource)
                    .filter(resource -> resource != Resource.NOTHING)
                    .toList();
            for (Resource startingResource : startingResources) {
                game.getPlayer(entry.getKey().color())
                        .inventory()
                        .putResource(startingResource, 1);
            }
        }
    }
}
