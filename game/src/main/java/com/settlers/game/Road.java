package com.settlers.game;

public record Road(Color color) {
    public Road {
        assert color != null;
    }

    public static Road of(Color color) {
        return new Road(color);
    }
}
