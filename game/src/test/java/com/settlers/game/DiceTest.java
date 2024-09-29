package com.settlers.game;

import org.junit.Assert;
import org.junit.Test;

public class DiceTest {
    @Test
    public void testDiceRollAlwaysBetween2And12() {
        for (int i = 0; i < 10_000; i++) {
            int roll = Dice.roll();
            Assert.assertTrue(2 <= roll && roll <= 12);
        }
    }
}
