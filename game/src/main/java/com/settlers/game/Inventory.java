package com.settlers.game;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {
    private final Map<Resource, Integer> resources;
    private final Map<DevelopmentCard, Integer> developmentCards;
    public Inventory(Map<Resource, Integer> resources,
                     Map<DevelopmentCard, Integer> developmentCards) {
        this.resources = Objects.requireNonNull(resources);
        this.developmentCards = Objects.requireNonNull(developmentCards);
    }

    public int totalResources() {
        return resources.values().stream().reduce(0, Integer::sum);
    }

    public int getResource(Resource resource) {
        return resources.get(resource);
    }

    public boolean putResource(Resource resource, int amount) {
        assert resource != null;
        assert resource != Resource.NOTHING;
        if (amount < 0 && resources.get(resource) < amount * -1) return false;

        resources.merge(resource, amount, Integer::sum);
        return true;
    }

    public boolean useDevelopmentCard(DevelopmentCard developmentCard) {
        assert developmentCard != null;
        if (developmentCard == DevelopmentCard.VICTORY_POINT) return false;
        if (!(developmentCards.get(developmentCard) >= 1)) return false;

        developmentCards.merge(developmentCard, -1, Integer::sum);
        return true;
    }

    public void addDevelopmentCard(DevelopmentCard developmentCard) {
        developmentCards.merge(developmentCard, 1, Integer::sum);
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
