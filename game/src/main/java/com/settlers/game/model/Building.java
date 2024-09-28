package com.settlers.game.model;

public record Building(Color color, Type type) {
    public Building {
        assert color != null;
        assert type != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public enum Type {
        CITY,
        SETTLEMENT
    }

    public static final class Builder {

        private Builder() {}

        public Building build(Color color, Type type) {
            return new Building(color, type);
        }
    }
}
