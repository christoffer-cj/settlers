package com.settlers.game;

public record Building(Color color, Type type) {
    public Building {
        assert color != null;
        assert type != null;
    }

    public static Building of(Color color, Type type) {
        return new Building(color, type);
    }

    public enum Type {
        SETTLEMENT(1),
        CITY(2);
        
        private final int resources;
        
        Type(int resources) {
            this.resources = resources;
        }
        
        public int resources() {
            return resources;
        }
    }
}
