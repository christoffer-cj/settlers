package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.impl.RandomDice;
import org.junit.Assert;
import org.junit.Test;

public class DiceTest {
    @Test
    public void testDiceRollAlwaysBetween2And12() {
        Dice uut = new RandomDice();
        for (int i = 0; i < 10_000; i++) {
            int roll = uut.roll();
            Assert.assertTrue(2 <= roll && roll <= 12);
        }
    }
}
