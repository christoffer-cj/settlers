package com.settlers.game;

import java.util.Collection;
import java.util.List;

public record Position(Coordinate coordinate, Direction direction) {
    public Position {
        assert coordinate != null;
        assert direction != null;
    }

    public static Position of(Coordinate coordinate, Direction direction) {
        return new Position(coordinate, direction);
    }

    public static Position of(int q, int r, Direction direction) {
        return new Position(Coordinate.of(q, r), direction);
    }

    public Collection<Position> getPositionsForVertex() {
        return switch (direction) {
            case ONE ->
                    List.of(this,
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.THREE),
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.FIVE));
            case TWO ->
                    List.of(this,
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.FOUR),
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.SIX));
            case THREE ->
                    List.of(this,
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.FIVE),
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.ONE));
            case FOUR ->
                    List.of(this,
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.SIX),
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.TWO));
            case FIVE ->
                    List.of(this,
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.ONE),
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.THREE));
            case SIX ->
                    List.of(this,
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.TWO),
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.FIVE));
        };
    }

    public Collection<Position> getPositionsForEdge() {
        return switch (direction) {
            case ONE ->
                    List.of(this,
                            Position.of(coordinate.q()+1, coordinate().r()-1, Direction.FOUR));
            case TWO ->
                    List.of(this,
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.FIVE));
            case THREE ->
                    List.of(this,
                            Position.of(coordinate.q(), coordinate().r()+1, Direction.SIX));
            case FOUR ->
                    List.of(this,
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.ONE));
            case FIVE ->
                    List.of(this,
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.TWO));
            case SIX ->
                    List.of(this,
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.THREE));
        };
    }

    public Collection<Position> getAdjacentVerticesForVertex() {
        return switch (direction) {
            case ONE ->
                    List.of(Position.of(coordinate, Direction.SIX),
                            Position.of(coordinate, Direction.TWO),
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.TWO));
            case TWO ->
                    List.of(Position.of(coordinate, Direction.ONE),
                            Position.of(coordinate, Direction.THREE),
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.THREE));
            case THREE ->
                    List.of(Position.of(coordinate, Direction.TWO),
                            Position.of(coordinate, Direction.FOUR),
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.FOUR));
            case FOUR ->
                    List.of(Position.of(coordinate, Direction.THREE),
                            Position.of(coordinate, Direction.FIVE),
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.FIVE));
            case FIVE ->
                    List.of(Position.of(coordinate, Direction.FOUR),
                            Position.of(coordinate, Direction.SIX),
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.SIX));
            case SIX ->
                    List.of(Position.of(coordinate, Direction.FIVE),
                            Position.of(coordinate, Direction.ONE),
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.ONE));
        };
    }

    public Collection<Position> getAdjacentVerticesForEdge() {
        return switch (direction) {
            case ONE ->
                    List.of(this,
                            Position.of(coordinate, Direction.TWO));
            case TWO ->
                    List.of(this,
                            Position.of(coordinate, Direction.THREE));
            case THREE ->
                    List.of(this,
                            Position.of(coordinate, Direction.FOUR));
            case FOUR ->
                    List.of(this,
                            Position.of(coordinate, Direction.FIVE));
            case FIVE ->
                    List.of(this,
                            Position.of(coordinate, Direction.SIX));
            case SIX ->
                    List.of(this,
                            Position.of(coordinate, Direction.ONE));
        };
    }

    public Collection<Position> getAdjacentEdgesForEdge() {
        return switch (direction) {
            case ONE ->
                    List.of(Position.of(coordinate, Direction.SIX),
                            Position.of(coordinate, Direction.TWO),
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.FIVE),
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.THREE));
            case TWO ->
                    List.of(Position.of(coordinate, Direction.ONE),
                            Position.of(coordinate, Direction.THREE),
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.SIX),
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.FOUR));
            case THREE ->
                    List.of(Position.of(coordinate, Direction.TWO),
                            Position.of(coordinate, Direction.FOUR),
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.ONE),
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.FIVE));
            case FOUR ->
                    List.of(Position.of(coordinate, Direction.THREE),
                            Position.of(coordinate, Direction.FIVE),
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.TWO),
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.SIX));
            case FIVE ->
                    List.of(Position.of(coordinate, Direction.FOUR),
                            Position.of(coordinate, Direction.SIX),
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.THREE),
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.ONE));
            case SIX ->
                    List.of(Position.of(coordinate, Direction.FIVE),
                            Position.of(coordinate, Direction.ONE),
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.FOUR),
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.TWO));
        };
    }

    public Collection<Position> getAdjacentEdgesForVertex() {
        return switch (direction) {
            case ONE ->
                    List.of(this,
                            Position.of(coordinate, Direction.SIX),
                            Position.of(coordinate.q()+1, coordinate.r()-1, Direction.FIVE));
            case TWO ->
                    List.of(this,
                            Position.of(coordinate, Direction.ONE),
                            Position.of(coordinate.q()+1, coordinate.r(), Direction.SIX));
            case THREE ->
                    List.of(this,
                            Position.of(coordinate, Direction.TWO),
                            Position.of(coordinate.q(), coordinate.r()+1, Direction.ONE));
            case FOUR ->
                    List.of(this,
                            Position.of(coordinate, Direction.THREE),
                            Position.of(coordinate.q()-1, coordinate.r()+1, Direction.TWO));
            case FIVE ->
                    List.of(this,
                            Position.of(coordinate, Direction.FOUR),
                            Position.of(coordinate.q()-1, coordinate.r(), Direction.THREE));
            case SIX ->
                    List.of(this,
                            Position.of(coordinate, Direction.FIVE),
                            Position.of(coordinate.q(), coordinate.r()-1, Direction.FOUR));
        };
    }
}
