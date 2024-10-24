package com.settlers.game;

import com.settlers.game.states.ActionPhase;
import com.settlers.game.states.YearOfPlenty;
import org.junit.Assert;
import org.junit.Test;

public class YearOfPlentyTest {
    @Test
    public void testWhenUseYearOfPlenty_ThenPlayerReceivesResources() {
        Player player = Player.create(Color.RED);
        Game game = Game.builder()
                .addPlayer(player)
                .build();

        YearOfPlenty uut = new YearOfPlenty(game, new ActionPhase(game));
        game.setState(uut);

        boolean yearOfPlentyUsed = uut.yearOfPlenty(player, Resource.BRICK, Resource.ORE);

        Assert.assertTrue(yearOfPlentyUsed);
        Assert.assertEquals(1, player.inventory().getResource(Resource.BRICK));
        Assert.assertEquals(1, player.inventory().getResource(Resource.ORE));
        Assert.assertTrue(game.getState() instanceof ActionPhase);
    }
}
