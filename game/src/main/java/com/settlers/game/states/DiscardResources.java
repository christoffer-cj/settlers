package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Inventory;
import com.settlers.game.Player;
import com.settlers.game.Resource;

import java.util.HashMap;
import java.util.Map;

public class DiscardResources extends AbstractState {
    private final Map<Player, Integer> discardAmounts;
    private final Map<Player, Boolean> hasDiscarded;

    public DiscardResources(Game game) {
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

        if (game.getPlayers()
                .stream()
                .noneMatch(player -> player.inventory().totalResources() > 7)) {
            game.setState(new MoveRobber(game));
        }
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
            if (playerInventory.getResource(entry.getKey()) < entry.getValue()) return false;
        }

        // discard player resources
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            playerInventory.putResource(entry.getKey(), entry.getValue() * -1);
        }

        hasDiscarded.put(player, true);
        if (hasDiscarded.values().stream().allMatch(x -> x)) {
            game.setState(new MoveRobber(game));
        }

        return true;
    }
}
