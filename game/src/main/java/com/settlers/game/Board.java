package com.settlers.game;

import java.util.*;
import java.util.function.Function;

public class Board {
    private final Map<Coordinate, Tile> tiles;
    private Coordinate robber;

    private Board(Map<Coordinate, Tile> tiles, Coordinate robber) {
        this.tiles = Objects.requireNonNull(tiles);
        this.robber = robber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Optional<Road> getRoad(Position position) {
        return getEdge(position, Tile::roads);
    }

    public boolean addRoad(Position position, Road road) {
        assert position != null;
        assert road != null;
        if (getRoad(position).isPresent()) return false;

        boolean hasAdjacentBuilding = position.getAdjacentVerticesForEdge()
                .stream()
                .map(this::getBuilding)
                .flatMap(Optional::stream)
                .anyMatch(building -> building.color() == road.color());

        boolean hasAdjacentRoad = position.getAdjacentEdgesForEdge()
                .stream()
                .map(this::getRoad)
                .flatMap(Optional::stream)
                .anyMatch(adjacentRoad -> adjacentRoad.color() == road.color());

        if (!hasAdjacentBuilding && !hasAdjacentRoad) return false;

        Tile tile = tiles.get(position.coordinate());
        tile.roads().put(position.direction(), road);

        return true;
    }

    public Optional<Building> getBuilding(Position position) {
        return getVertex(position, Tile::buildings);
    }

    public boolean addBuilding(Position position, Building building) {
        return addBuilding(position, building, false);
    }

    public boolean addBuilding(Position position, Building building, boolean isSetupPhase) {
        assert position != null;
        assert building != null;

        Optional<Building> presentBuilding = getBuilding(position);
        if (building.type() == Building.Type.SETTLEMENT && presentBuilding.isPresent()) {
            return false;
        }

        if (building.type() == Building.Type.CITY &&
           (presentBuilding.isEmpty() ||
            presentBuilding.get().color() != building.color() ||
            presentBuilding.get().type() != Building.Type.SETTLEMENT)) {
            return false;
        }
        boolean hasAdjacentBuilding = position.getAdjacentVerticesForVertex()
                .stream()
                .map(this::getBuilding)
                .anyMatch(Optional::isPresent);
        if (hasAdjacentBuilding) return false;

        if (!isSetupPhase) {
            boolean hasAdjacentRoad = position.getAdjacentEdgesForVertex()
                    .stream()
                    .map(this::getRoad)
                    .flatMap(Optional::stream)
                    .anyMatch(road -> road.color() == building.color());
            if (!hasAdjacentRoad) return false;
        }

        Tile tile = tiles.get(position.coordinate());
        tile.buildings().put(position.direction(), building);

        return true;
    }

    public Optional<Harbor> getHarbor(Position position) {
        return getVertex(position, Tile::harbors);
    }

    public Collection<Coordinate> getCoordinates(int number) {
        return tiles.entrySet()
                .stream()
                .filter(entry -> entry.getValue().number() == number)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Collection<Building> getBuildings(Coordinate coordinate, Color color) {
        assert coordinate != null;
        assert color != null;

        if (tiles.get(coordinate) == null) {
            return Collections.emptyList();
        }

        Set<Building> buildings = new HashSet<>();
        for (Direction direction : Direction.values()) {
            getBuilding(Position.of(coordinate, direction)).filter(building -> building.color() == color).ifPresent(buildings::add);
        }

        return Collections.unmodifiableCollection(buildings);
    }

    public Collection<Building> getBuildings(Color color) {
        Collection<Building> buildings = new ArrayList<>();
        for (Tile tile : tiles.values()) {
            for (Building building : tile.buildings().values()) {
                if (building == null) continue;
                if (building.color() == color) buildings.add(building);
            }
        }

        return buildings;
    }

    public Optional<Tile> getTile(Coordinate coordinate) {
        return Optional.ofNullable(tiles.get(coordinate));
    }

    public Coordinate getRobber() {
        return robber;
    }

    public boolean setRobber(Coordinate coordinate) {
        assert coordinate != null;
        if (!tiles.containsKey(coordinate)) return false;
        if (coordinate.equals(robber)) return false;

        robber = coordinate;
        return true;
    }

    public boolean hasHarbor(Player player, Harbor harbor) {
        assert player != null;
        assert harbor != null;
        for (Map.Entry<Coordinate, Tile> entry : tiles.entrySet()) {
            for (Map.Entry<Direction, Harbor> harborEntry : entry.getValue().harbors().entrySet()) {
                if (!harbor.equals(harborEntry.getValue())) continue;
                Optional<Building> building = getBuilding(Position.of(entry.getKey(), harborEntry.getKey()));
                if (building.isEmpty()) continue;
                if (!building.get().color().equals(player.color())) continue;

                return true;
            }
        }

        return false;
    }

    public int getLongestRoad(Player player) {
        int longestRoad = 0;
        for (Map.Entry<Coordinate, Tile> tileEntry : tiles.entrySet()) {
            for (Map.Entry<Direction, Road> roadEntry : tileEntry.getValue().roads().entrySet()) {
                if (!(roadEntry.getValue().color() == player.color())) continue;
                int roadLength = getLongestRoad(player, Position.of(tileEntry.getKey(), roadEntry.getKey()), new HashSet<>());
                if (roadLength > longestRoad) longestRoad = roadLength;
            }
        }
        return longestRoad;
    }

    private int getLongestRoad(Player player, Position position, Set<Position> visited) {
        visited.add(position);
        Collection<Position> adjacentPositions = new ArrayList<>(position.getAdjacentEdgesForEdge());
        visited.forEach(visitedPosition -> adjacentPositions.removeIf(visitedPosition::isSameEdge));
        for (Position adjacentPosition : adjacentPositions) {
            Optional<Road> adjacentRoad = getRoad(adjacentPosition);
            if (adjacentRoad.isEmpty()) continue;
            if (!(adjacentRoad.get().color() == player.color())) continue;
            return 1 + getLongestRoad(player, adjacentPosition, visited);
        }

        return 1;
    }

    private <T> Optional<T> getVertex(Position position, Function<Tile, Map<Direction, T>> mapper) {
        assert position != null;
        assert mapper != null;

        Collection<Position> vertexPositions = position.getPositionsForVertex();
        for (Position vertexPosition : vertexPositions) {
            Coordinate vertexCoordinate = vertexPosition.coordinate();
            Direction vertexDirection = vertexPosition.direction();
            Tile tile = tiles.get(vertexCoordinate);
            if (tile == null) continue;
            Map<Direction, T> map = mapper.apply(tile);
            T vertex = map.get(vertexDirection);
            if (vertex == null) continue;
            return Optional.of(vertex);
        }

        return Optional.empty();
    }

    private <T> Optional<T> getEdge(Position position, Function<Tile, Map<Direction, T>> mapper) {
        assert position != null;
        assert mapper != null;

        Collection<Position> edgePositions = position.getPositionsForEdge();
        for (Position edgePosition : edgePositions) {
            Coordinate edgeCoordinate = edgePosition.coordinate();
            Direction edgeDirection = edgePosition.direction();
            Tile tile = tiles.get(edgeCoordinate);
            if (tile == null) continue;
            Map<Direction, T> map = mapper.apply(tile);
            T t = map.get(edgeDirection);
            if (t == null) continue;
            return Optional.of(t);
        }

        return Optional.empty();
    }

    public static final class Builder {
        private final Map<Coordinate, Tile> tiles = new HashMap<>();
        private Coordinate robber;

        private Builder() {}

        public Board build() {
            return new Board(tiles, robber);
        }

        public Builder addTile(Coordinate coordinate, Tile tile) {
            assert coordinate != null;
            if (tiles.containsKey(coordinate)) return this;
            assert tile != null;

            tiles.put(coordinate, tile);
            return this;
        }

        public Builder setRobber(Coordinate coordinate) {
            assert tiles.containsKey(coordinate);
            robber = coordinate;
            return this;
        }
    }
}
