package com.settlers.game.dice.impl;

import com.settlers.game.dice.Dice;

import java.util.Random;

public class RandomDice implements Dice {
    private final Random rng;

    public RandomDice() {
        this.rng = new Random();
    }

    @Override
    public int roll() {
        int firstRoll = rng.nextInt(1, 7);
        int secondRoll = rng.nextInt(1, 7);

        return firstRoll + secondRoll;
    }
}
