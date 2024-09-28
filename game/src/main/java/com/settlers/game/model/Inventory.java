package com.settlers.game.model;

import java.util.Map;

public record Inventory(Map<Resource, Integer> resources) {
    public Inventory {
        assert resources != null;
    }
}
