package com.settlers.game;

public record Road(Color color) {
    public Road {
        assert color != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        public Road build(Color color) {
            return new Road(color);
        }
    }
}
