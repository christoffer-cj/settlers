package com.settlers.game;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Inventory {
    private final Map<Resource, Integer> resources;
    private final Map<DevelopmentCard, Integer> developmentCards;
    private int settlements;
    private int cities;
    private int roads;
    public Inventory(Map<Resource, Integer> resources,
                     Map<DevelopmentCard, Integer> developmentCards,
                     int settlements,
                     int cities,
                     int roads) {
        assert settlements >= 0;
        assert cities >= 0;
        assert roads >= 0;
        this.resources = Objects.requireNonNull(resources);
        this.developmentCards = Objects.requireNonNull(developmentCards);
        this.settlements = settlements;
        this.cities = cities;
        this.roads = roads;
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

    public void clearResource(Resource resource) {
        resources.put(resource, 0);
    }

    public Resource stealResource() {
        Random rng = new Random();
        int resourceNoToSteal = rng.nextInt(1, totalResources() + 1);
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            if (resourceNoToSteal <= entry.getValue()) {
                Resource stolenResource = entry.getKey();
                putResource(stolenResource, -1);
                return stolenResource;
            } else {
                resourceNoToSteal -= entry.getValue();
            }
        }

        throw new IllegalStateException("No resource stolen");
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

    public int getBuilding(Building.Type type) {
        return switch (type) {
            case SETTLEMENT -> settlements;
            case CITY -> cities;
        };
    }

    public boolean hasBuilding(Building.Type type) {
        return getBuilding(type) > 0;
    }

    public boolean useBuilding(Building.Type type) {
        if (!hasBuilding(type)) return false;
        switch (type) {
            case SETTLEMENT -> {
                settlements -= 1;
            }
            case CITY -> {
                cities -= 1;
                settlements += 1;
            }
        };

        return true;
    }

    public int getRoads() {
        return roads;
    }

    public boolean hasRoad() {
        return getRoads() > 0;
    }

    public boolean useRoad() {
        if (!hasRoad()) return false;
        roads -= 1;
        return true;
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
        return new Inventory(resources, developmentCards, 5, 4,15);
    }

    public static final class Builder {
        private final Map<Resource, Integer> resources;
        private final Map<DevelopmentCard, Integer> developmentCards;
        private int settlements = 5;
        private int cities = 4;
        private int roads = 15;
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
            return new Inventory(
                    resources,
                    developmentCards,
                    settlements,
                    cities,
                    roads);
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

        public Builder setSettlements(int amount) {
            assert amount >= 0;
            settlements = amount;
            return this;
        }

        public Builder setCities(int amount) {
            assert amount >= 0;
            cities = amount;
            return this;
        }

        public Builder setRoads(int amount) {
            assert amount >= 0;
            roads = amount;
            return this;
        }
    }
}
