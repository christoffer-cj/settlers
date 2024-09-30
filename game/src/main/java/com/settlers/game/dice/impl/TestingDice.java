package com.settlers.game.dice.impl;

import com.settlers.game.dice.Dice;

import java.util.List;
import java.util.Objects;

public class TestingDice implements Dice {
    private final List<Integer> rolls;
    private int idx;

    public TestingDice(List<Integer> rolls) {
        this.rolls = Objects.requireNonNull(rolls);
        assert rolls.stream().allMatch(roll -> 2 <= roll && roll <= 12);
        this.idx = 0;
    }


    @Override
    public int roll() {
        if (rolls.size() == idx) {
            idx = 0;
        }
        int roll = rolls.get(idx);
        idx++;

        return roll;
    }
}
