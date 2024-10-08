package com.settlers.game.states.developmentcards;

import com.settlers.game.Game;
import com.settlers.game.Player;
import com.settlers.game.Resource;
import com.settlers.game.states.BaseState;
import com.settlers.game.states.State;

import java.util.Objects;

public class YearOfPlenty extends BaseState {
    private final State previousState;

    public YearOfPlenty(Game game, State previousState) {
        super(game);
        this.previousState = Objects.requireNonNull(previousState);
    }

    @Override
    public boolean yearOfPlenty(Player player, Resource firstResource, Resource secondResource) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        game.getPlayer(player.color()).inventory().resources().merge(firstResource, 1, Integer::sum);
        game.getPlayer(player.color()).inventory().resources().merge(secondResource, 1, Integer::sum);

        return true;
    }
}
