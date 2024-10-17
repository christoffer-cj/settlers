package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.BuildingPhase;
import com.settlers.game.states.YearOfPlenty;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class YearOfPlentyTest {
    @Test
    public void testWhenUseYearOfPlenty_ThenPlayerReceivesResources() {
        Board board = Board.builder().build();
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        YearOfPlenty uut = new YearOfPlenty(game, new BuildingPhase(game, true));
        game.setState(uut);

        boolean yearOfPlentyUsed = uut.yearOfPlenty(player, Resource.BRICK, Resource.ORE);

        Assert.assertTrue(yearOfPlentyUsed);
        Assert.assertEquals(1, player.inventory().getResource(Resource.BRICK));
        Assert.assertEquals(1, player.inventory().getResource(Resource.ORE));
        Assert.assertTrue(game.getState() instanceof BuildingPhase);
    }
}
