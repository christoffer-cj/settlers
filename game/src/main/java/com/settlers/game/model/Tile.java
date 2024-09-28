package com.settlers.game.model;

import java.util.HashMap;
import java.util.Map;

public record Tile(Resource resource,
                   int number,
                   Map<Position, Road> roads,
                   Map<Position, Building> buildings,
                   Map<Position, Harbor> harbors) {
    public Tile {
        assert resource != null;
        assert 2 <= number && number <= 12;
        assert resource != Resource.NOTHING || number == 7;
        assert roads != null;
        assert buildings != null;
        assert harbors != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Map<Position, Road> roads = new HashMap<>();
        private final Map<Position, Building> buildings = new HashMap<>();
        private final Map<Position, Harbor> harbors = new HashMap<>();

        private Builder() {}

        public Tile build(Resource resource, int number) {
            return new Tile(resource, number, roads, buildings, harbors);
        }

        public Builder addRoad(Position position, Road road) {
            if (roads.containsKey(position)) return this;

            roads.put(position, road);
            return this;
        }

        public Builder addBuilding(Position position, Building building) {
            if (buildings.containsKey(position)) return this;

            buildings.put(position, building);
            return this;
        }

        public Builder addHarbor(Position position, Harbor harbor) {
            if (harbors.containsKey(position)) return this;

            harbors.put(position, harbor);
            return this;
        }
    }
}
