package com.settlers.game.model;

import java.util.List;

public record Coordinate(int q, int r) {
    public static Coordinate of(int q, int r) {
        return new Coordinate(q, r);
    }

    public List<Coordinate> getCoordinatesForVertex(Position vertex) {
        assert vertex != null;

        return switch (vertex) {
            case ONE -> List.of(this, Coordinate.of(q, r-1), Coordinate.of(q+1, r-1));
            case TWO -> List.of(this, Coordinate.of(q+1, r-1), Coordinate.of(q+1, r));
            case THREE -> List.of(this, Coordinate.of(q+1, r), Coordinate.of(q, r+1));
            case FOUR -> List.of(this, Coordinate.of(q, r+1), Coordinate.of(q-1, r+1));
            case FIVE -> List.of(this, Coordinate.of(q-1, r+1), Coordinate.of(q-1, r));
            case SIX -> List.of(this, Coordinate.of(q-1, r), Coordinate.of(q, r-1));
        };
    }

    public List<Coordinate> getCoordinatesForEdge(Position edge) {
        assert edge != null;

        return switch (edge) {
            case ONE -> List.of(this, Coordinate.of(q+1, r-1));
            case TWO -> List.of(this, Coordinate.of(q+1, r));
            case THREE -> List.of(this, Coordinate.of(q, r+1));
            case FOUR -> List.of(this, Coordinate.of(q-1, r+1));
            case FIVE -> List.of(this, Coordinate.of(q-1, r));
            case SIX -> List.of(this, Coordinate.of(q, r-1));
        };
    }
}
