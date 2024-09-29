package com.settlers.game;

import java.util.HashMap;
import java.util.Map;

public record Tile(Resource resource,
                   int number,
                   Map<Direction, Road> roads,
                   Map<Direction, Building> buildings,
                   Map<Direction, Harbor> harbors) {
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

        private final Map<Direction, Road> roads = new HashMap<>();
        private final Map<Direction, Building> buildings = new HashMap<>();
        private final Map<Direction, Harbor> harbors = new HashMap<>();

        private Builder() {}

        public Tile build(Resource resource, int number) {
            return new Tile(resource, number, roads, buildings, harbors);
        }

        public Builder addRoad(Direction direction, Road road) {
            assert direction != null;
            assert road != null;
            if (roads.get(direction) != null) return this;

            roads.put(direction, road);
            return this;
        }

        public Builder addBuilding(Direction direction, Building building) {
            assert direction != null;
            assert building != null;
            if (buildings.get(direction) != null) return this;

            buildings.put(direction, building);
            return this;
        }

        public Builder addHarbor(Direction direction, Harbor harbor) {
            assert direction != null;
            assert harbor != null;
            if (harbors.get(direction) != null) return this;

            harbors.put(direction, harbor);
            return this;
        }
    }
}
