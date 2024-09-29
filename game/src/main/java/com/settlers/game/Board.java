package com.settlers.game;

import java.util.*;
import java.util.function.Function;

public class Board {
    private final Map<Coordinate, Tile> tiles;

    private Board(Map<Coordinate, Tile> tiles) {
        this.tiles = Objects.requireNonNull(tiles);
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

        Tile tile = tiles.get(position.coordinate());
        tile.roads().put(position.direction(), road);

        return true;
    }

    public Optional<Building> getBuilding(Position position) {
        return getVertex(position, Tile::buildings);
    }

    public boolean addBuilding(Position position, Building building) {
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
        // check distance rule
        // check building is next to a road unless in setup phase

        Tile tile = tiles.get(position.coordinate());
        tile.buildings().put(position.direction(), building);

        return true;
    }

    public Optional<Harbor> getHarbor(Position position) {
        return getVertex(position, Tile::harbors);
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

        private Builder() {}

        public Board build() {
            return new Board(tiles);
        }

        public Builder addTile(Coordinate coordinate, Tile tile) {
            assert coordinate != null;
            if (tiles.containsKey(coordinate)) return this;
            assert tile != null;

            tiles.put(coordinate, tile);
            return this;
        }
    }
}