package com.settlers.game;

import java.util.Map;

public record Inventory(Map<Resource, Integer> resources,
                        Map<Building.Type, Integer> buildings,
                        Map<DevelopmentCard, Integer> developmentCards) {
    public Inventory {
        assert resources != null;
    }
}
