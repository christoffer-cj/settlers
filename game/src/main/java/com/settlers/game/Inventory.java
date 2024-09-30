package com.settlers.game;

import java.util.EnumMap;
import java.util.Map;

public record Inventory(Map<Resource, Integer> resources,
                        Map<DevelopmentCard, Integer> developmentCards) {
    public Inventory {
        assert resources != null;
        assert developmentCards != null;
    }

    public static Inventory create() {
        Map<Resource, Integer> resources = new EnumMap<>(Resource.class);
        for (Resource resource : Resource.values()) {
            resources.put(resource, 0);
        }
        Map<DevelopmentCard, Integer> developmentCards = new EnumMap<>(DevelopmentCard.class);
        for (DevelopmentCard developmentCard : DevelopmentCard.values()) {
            developmentCards.put(developmentCard, 0);
        }
        return new Inventory(resources, developmentCards);
    }
}
