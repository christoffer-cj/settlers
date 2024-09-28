package com.settlers.game.model;

import java.util.List;

public enum Position {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX;

    public List<Position> getPositionsForVertex(Position vertex) {
        return switch (vertex) {
            case ONE -> List.of(this, THREE, FIVE);
            case TWO -> List.of(this, FOUR, SIX);
            case THREE -> List.of(this, FIVE, ONE);
            case FOUR -> List.of(this, SIX, TWO);
            case FIVE -> List.of(this, ONE, THREE);
            case SIX -> List.of(this, TWO, FIVE);
        };
    }

    public List<Position> getPositionsForEdge(Position edge) {
        return switch (edge) {
            case ONE -> List.of(this, FOUR);
            case TWO -> List.of(this, FIVE);
            case THREE -> List.of(this, SIX);
            case FOUR -> List.of(this, ONE);
            case FIVE -> List.of(this, TWO);
            case SIX -> List.of(this, THREE);
        };
    }
}