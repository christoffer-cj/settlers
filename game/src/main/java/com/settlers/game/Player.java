package com.settlers.game;

public record Player(Color color, Inventory inventory) {
    public Player {
        assert color != null;
        assert inventory != null;
    }

    public static Player create(Color color) {
        return new Player(color, Inventory.create());
    }
}
