package com.settlers.game;

import org.junit.Assert;
import org.junit.Test;

public class PositionTest {
    @Test
    public void testWhenEdgesAreTheSame_ThenIsSameEdgeReturnTrue() {
        Position positionOne = Position.of(0, 0, Direction.ONE);
        Position positionTwo = Position.of(1, -1, Direction.FOUR);

        Assert.assertTrue(positionOne.isSameEdge(positionTwo));
        Assert.assertTrue(positionTwo.isSameEdge(positionOne));
    }
}
