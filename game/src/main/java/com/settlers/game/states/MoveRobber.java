package com.settlers.game.states;

import com.settlers.game.*;

import java.util.HashMap;
import java.util.Map;

public class MoveRobber extends BaseState {
    private final Map<Player, Integer> discardAmounts;
    private final Map<Player, Boolean> hasDiscarded;

    public MoveRobber(Game game) {
        super(game);
        Map<Player, Integer> discardAmounts = new HashMap<>();
        Map<Player, Boolean> hasDiscarded = new HashMap<>();
        for (Player player : game.getPlayers()) {
            if (player.inventory().amountOfResources() <= 7) continue;
            int amountToDiscard = player.inventory().amountOfResources() / 2;
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
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!hasDiscarded.values().stream().allMatch(x -> x)) return false;

        if (!game.getBoard().setRobber(coordinate)) return false;


        game.setState(new TradingPhase(game));
        return true;
    }
}
