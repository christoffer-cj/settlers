package com.settlers.game.states.impl;

import com.settlers.game.Game;
import com.settlers.game.Player;
import com.settlers.game.Resource;

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

        // todo get player from game, and check if player has sufficient resources to discard
        return false;
    }
}
