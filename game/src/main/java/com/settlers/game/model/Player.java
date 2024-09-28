package com.settlers.game.model;

public record Player(Color color, Inventory inventory) {
    public Player {
        assert color != null;
        assert inventory != null;
    }
}
