package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;

public class WinnerPhase extends AbstractState {
    public WinnerPhase(Game game, Player winner) {
        super(game);
    }
}
