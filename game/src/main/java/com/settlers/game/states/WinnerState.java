package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;

public class WinnerState extends AbstractState {
    public WinnerState(Game game, Player winner) {
        super(game);
    }
}
