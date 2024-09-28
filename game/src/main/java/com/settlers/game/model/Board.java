package com.settlers.game.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public record Board(Map<Coordinate, Tile> tiles) {
    public Board {
        assert tiles != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Optional<Road> getRoad(Coordinate coordinate, Position position) {
        return getEdge(coordinate, position, Tile::roads);
    }

    public Optional<Building> getBuilding(Coordinate coordinate, Position position) {
        return getVertex(coordinate, position, Tile::buildings);
    }

    public Optional<Harbor> getHarbor(Coordinate coordinate, Position position) {
        return getVertex(coordinate, position, Tile::harbors);
    }

    private <T> Optional<T> getVertex(Coordinate coordinate, Position position, Function<Tile, Map<Position, T>> mapper) {
        assert coordinate != null;
        assert position != null;
        assert mapper != null;

        List<Coordinate> vertexCoordinates = coordinate.getCoordinatesForVertex(position);
        List<Position> vertexPositions = position.getPositionsForVertex(position);
        assert vertexCoordinates.size() == vertexPositions.size();
        for (int idx = 0; idx < vertexCoordinates.size(); idx++) {
            Coordinate vertexCoordinate = vertexCoordinates.get(idx);
            Position vertexPosition = vertexPositions.get(idx);
            Tile tile = tiles.get(vertexCoordinate);
            if (tile == null) continue;
            Map<Position, T> map = mapper.apply(tile);
            T t = map.get(vertexPosition);
            if (t == null) continue;
            return Optional.of(t);
        }

        return Optional.empty();
    }

    private <T> Optional<T> getEdge(Coordinate coordinate, Position position, Function<Tile, Map<Position, T>> mapper) {
        assert coordinate != null;
        assert position != null;
        assert mapper != null;

        List<Coordinate> edgeCoordinates = coordinate.getCoordinatesForEdge(position);
        List<Position> edgePositions = position.getPositionsForEdge(position);
        assert edgeCoordinates.size() == edgePositions.size();
        for (int idx = 0; idx < edgeCoordinates.size(); idx++) {
            Coordinate edgeCoordinate = edgeCoordinates.get(idx);
            Position edgePosition = edgePositions.get(idx);
            Tile tile = tiles.get(edgeCoordinate);
            if (tile == null) continue;
            Map<Position, T> map = mapper.apply(tile);
            T t = map.get(edgePosition);
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
