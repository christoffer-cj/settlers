package com.settlers.game.dice;

import java.util.Random;

public class RandomDice implements Dice {
    private final Random rng;

    private RandomDice() {
        this.rng = new Random();
    }

    public static RandomDice create() {
        return new RandomDice();
    }

    @Override
    public int roll() {
        int firstRoll = rng.nextInt(1, 7);
        int secondRoll = rng.nextInt(1, 7);

        return firstRoll + secondRoll;
    }
}
