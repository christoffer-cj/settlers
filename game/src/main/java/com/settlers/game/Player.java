package com.settlers.game;

import java.util.Objects;

public record Player(Color color, Inventory inventory) {
    public Player {
        assert color != null;
        assert inventory != null;
    }

    public static Player of(Color color, Inventory inventory) {
        return new Player(color, inventory);
    }

    public static Player create(Color color) {
        return new Player(color, Inventory.empty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color);
    }
}
