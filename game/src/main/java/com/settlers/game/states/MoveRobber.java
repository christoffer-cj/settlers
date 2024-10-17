package com.settlers.game.states;

import com.settlers.game.*;

import java.util.*;

public class MoveRobber extends BaseState {
    private boolean robberMoved = false;
    private final Set<Player> playersEligibleToStealFrom = new HashSet<>();
    private final Random rng = new Random();
    private State previousState = null;


    public MoveRobber(Game game) {
        super(game);
    }

    public MoveRobber(Game game, State previousState) {
        super(game);
        this.previousState = Objects.requireNonNull(previousState);
    }

    @Override
    public boolean moveRobber(Player player, Coordinate coordinate) {
        if (robberMoved) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!game.getBoard().setRobber(coordinate)) return false;

        for (Direction direction : Direction.values()) {
            Optional<Building> buildingAdjacentToRobber = game.getBoard().getBuilding(Position.of(coordinate, direction));
            if (buildingAdjacentToRobber.isEmpty()) continue;
            if (buildingAdjacentToRobber.get().color().equals(game.getCurrentPlayer().color())) continue;
            playersEligibleToStealFrom.add(game.getPlayer(buildingAdjacentToRobber.get().color()));
        }

        if (playersEligibleToStealFrom.isEmpty()) {
            game.setState(new TradingPhase(game));
        }
        robberMoved = true;

        return true;
    }

    @Override
    public boolean stealResource(Player player, Player playerToStealFrom) {
        if (!robberMoved) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!playersEligibleToStealFrom.contains(playerToStealFrom)) return false;

        int totalResources = game.getPlayer(playerToStealFrom.color())
                .inventory()
                .totalResources();

        if (totalResources == 0) {
            game.setState(new TradingPhase(game));
            return true;
        }

        Resource stolenResource = game.getPlayer(playerToStealFrom.color()).inventory().stealResource();
        game.getPlayer(player.color()).inventory().putResource(stolenResource, 1);

        if (previousState != null) {
            game.setState(previousState);
        } else {
            game.setState(new TradingPhase(game));
        }

        return true;
    }
}
