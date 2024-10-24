package com.settlers.game;

import com.settlers.game.states.ActionPhase;
import com.settlers.game.states.Monopoly;
import org.junit.Assert;
import org.junit.Test;

public class MonopolyTest {
    @Test
    public void testWhenNoResourcesOfTheKind_ThenPlayerReceivesNoResources() {
        Inventory redInventory = Inventory.builder()
                .addLumber(3)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addBrick(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Inventory orangeInventory = Inventory.builder()
                .addGrain(1)
                .build();
        Player orangePlayer = Player.of(Color.ORANGE, orangeInventory);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(whitePlayer)
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .addPlayer(orangePlayer)
                .build();

        Monopoly uut = new Monopoly(game, new ActionPhase(game));
        game.setState(uut);

        boolean monopolyUsed = uut.monopoly(whitePlayer, Resource.WOOL);

        Assert.assertTrue(monopolyUsed);
        Assert.assertEquals(0, whitePlayer.inventory().getResource(Resource.WOOL));
        Assert.assertTrue(game.getState() instanceof ActionPhase);
    }

    @Test
    public void testWhenPlayersHasResource_ThenPlayerReceivesResources() {
        Inventory redInventory = Inventory.builder()
                .addLumber(3)
                .addBrick(5)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addBrick(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Inventory orangeInventory = Inventory.builder()
                .addGrain(1)
                .build();
        Player orangePlayer = Player.of(Color.ORANGE, orangeInventory);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(whitePlayer)
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .addPlayer(orangePlayer)
                .build();

        Monopoly uut = new Monopoly(game, new ActionPhase(game));
        game.setState(uut);

        boolean monopolyUsed = uut.monopoly(whitePlayer, Resource.BRICK);

        Assert.assertTrue(monopolyUsed);
        Assert.assertEquals(9, whitePlayer.inventory().getResource(Resource.BRICK));
        Assert.assertTrue(game.getState() instanceof ActionPhase);
    }
}
