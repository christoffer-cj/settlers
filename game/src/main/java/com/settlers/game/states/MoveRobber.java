package com.settlers.game.states;

import com.settlers.game.*;

import java.util.*;

public class MoveRobber extends BaseState {
    private final Map<Player, Integer> discardAmounts;
    private final Map<Player, Boolean> hasDiscarded;
    private boolean robberMoved = false;
    private final Set<Player> playersEligibleToStealFrom = new HashSet<>();
    private final Random rng = new Random();


    public MoveRobber(Game game) {
        super(game);
        Map<Player, Integer> discardAmounts = new HashMap<>();
        Map<Player, Boolean> hasDiscarded = new HashMap<>();
        for (Player player : game.getPlayers()) {
            if (player.inventory().totalResources() <= 7) continue;
            int amountToDiscard = player.inventory().totalResources() / 2;
            discardAmounts.put(player, amountToDiscard);
            hasDiscarded.put(player, false);
        }
        this.discardAmounts = discardAmounts;
        this.hasDiscarded = hasDiscarded;
    }

    @Override
    public boolean discardResources(Player player, Map<Resource, Integer> resources) {
        if (discardAmounts.get(player) == null) return false;

        if (hasDiscarded.get(player)) return false;

        boolean isCorrectAmountToDiscard =
                discardAmounts.get(player)
                        .equals(resources.values()
                                .stream()
                                .reduce(0, Integer::sum));
        if (!isCorrectAmountToDiscard) return false;

        Inventory playerInventory = game.getPlayer(player.color()).inventory();
        // check player has sufficient resources
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            if (playerInventory.resources().get(entry.getKey()) < entry.getValue()) return false;
        }

        // discard player resources
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            playerInventory.resources().merge(entry.getKey(), entry.getValue(), (a, b) -> a - b);
        }

        return true;
    }

    @Override
    public boolean moveRobber(Player player, Coordinate coordinate) {
        if (robberMoved) return false;

        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!hasDiscarded.values().stream().allMatch(x -> x)) return false;

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

        int resourceNoToSteal = rng.nextInt(1, totalResources + 1);
        for (Map.Entry<Resource, Integer> entry : game.getPlayer(playerToStealFrom.color()).inventory().resources().entrySet()) {
            if (resourceNoToSteal <= entry.getValue()) {
                Resource stolenResource = entry.getKey();
                game.getPlayer(playerToStealFrom.color())
                        .inventory()
                        .resources()
                        .merge(stolenResource, -1, Integer::sum);
                game.getPlayer(player.color())
                        .inventory()
                        .resources()
                        .merge(stolenResource, 1, Integer::sum);
                break;
            } else {
                resourceNoToSteal -= entry.getValue();
            }
        }

        game.setState(new TradingPhase(game));
        return true;
    }
}
