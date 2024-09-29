package com.settlers.game;

import java.util.Random;

public final class Dice {
    private static final Random RNG = new Random();
    private Dice() {}

    public static int roll() {
        int firstRoll = RNG.nextInt(1, 7);
        int secondRoll = RNG.nextInt(1, 7);

        return firstRoll + secondRoll;
    }
}
