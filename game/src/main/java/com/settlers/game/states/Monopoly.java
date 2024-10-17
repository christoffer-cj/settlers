package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;
import com.settlers.game.Resource;

import java.util.Objects;

public class Monopoly extends BaseState {
    private final State previousState;

    public Monopoly(Game game, State previousState) {
        super(game);
        this.previousState = Objects.requireNonNull(previousState);
    }

    @Override
    public boolean monopoly(Player player, Resource resource) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        int resourcesToGet = 0;
        for (Player otherPlayer : game.getPlayers()) {
            if (otherPlayer.equals(player)) continue;
            resourcesToGet += otherPlayer.inventory().getResource(resource);
            otherPlayer.inventory().clearResource(resource);
        }
        game.getPlayer(player.color()).inventory().putResource(resource, resourcesToGet);

        game.setState(previousState);

        return true;
    }
}
