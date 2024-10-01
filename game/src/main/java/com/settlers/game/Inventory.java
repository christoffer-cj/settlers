package com.settlers.game;

import java.util.EnumMap;
import java.util.Map;

public record Inventory(Map<Resource, Integer> resources,
                        Map<DevelopmentCard, Integer> developmentCards) {
    public Inventory {
        assert resources != null;
        assert developmentCards != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Inventory empty() {
        Map<Resource, Integer> resources = new EnumMap<>(Resource.class);
        for (Resource resource : Resource.values()) {
            if (resource == Resource.NOTHING) continue;
            resources.put(resource, 0);
        }
        Map<DevelopmentCard, Integer> developmentCards = new EnumMap<>(DevelopmentCard.class);
        for (DevelopmentCard developmentCard : DevelopmentCard.values()) {
            developmentCards.put(developmentCard, 0);
        }
        return new Inventory(resources, developmentCards);
    }

    public int amountOfResources() {
        return resources.values().stream().reduce(0, Integer::sum);
    }

    public static final class Builder {
        private final Map<Resource, Integer> resources;
        private final Map<DevelopmentCard, Integer> developmentCards;
        private Builder() {
            Map<Resource, Integer> resources = new EnumMap<>(Resource.class);
            for (Resource resource : Resource.values()) {
                if (resource == Resource.NOTHING) continue;
                resources.put(resource, 0);
            }
            this.resources = resources;
            Map<DevelopmentCard, Integer> developmentCards = new EnumMap<>(DevelopmentCard.class);
            for (DevelopmentCard developmentCard : DevelopmentCard.values()) {
                developmentCards.put(developmentCard, 0);
            }
            this.developmentCards = developmentCards;
        }

        public Inventory build() {
            return new Inventory(resources, developmentCards);
        }

        public Builder addBrick(int amount) {
            assert amount >= 0;
            resources.merge(Resource.BRICK, amount, Integer::sum);
            return this;
        }

        public Builder addLumber(int amount) {
            assert amount >= 0;
            resources.merge(Resource.LUMBER, amount, Integer::sum);
            return this;
        }

        public Builder addOre(int amount) {
            assert amount >= 0;
            resources.merge(Resource.ORE, amount, Integer::sum);
            return this;
        }

        public Builder addGrain(int amount) {
            assert amount >= 0;
            resources.merge(Resource.GRAIN, amount, Integer::sum);
            return this;
        }

        public Builder addWool(int amount) {
            assert amount >= 0;
            resources.merge(Resource.WOOL, amount, Integer::sum);
            return this;
        }
    }
}
