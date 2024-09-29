package com.settlers.game;

public record Coordinate(int q, int r) {
    public static Coordinate of(int q, int r) {
        return new Coordinate(q, r);
    }
}
