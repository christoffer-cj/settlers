package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.MoveRobber;
import com.settlers.game.states.TradingPhase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveRobberTest {
    @Test
    public void testWhenPlayerHas7Resources_ThenPlayerDiscardReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Player player = Player.create(Color.RED);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean resourcesDiscarded = uut.discardResources(player, Collections.emptyMap());

        Assert.assertFalse(resourcesDiscarded);
    }

    @Test
    public void testWhenPlayerHasTooManyResources_ThenMoveRobberBeforeDiscardReturnFalse() {
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 8);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Inventory superflousInventory = Inventory.builder()
                .addBrick(5)
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, superflousInventory);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean robberMoved = uut.moveRobber(player, coordinate);

        Assert.assertFalse(robberMoved);
    }

    @Test
    public void testWhenPlayerHasTooManyResources_ThenDiscardTooFewReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder()
                .build();
        Inventory superflousInventory = Inventory.builder()
                .addBrick(5)
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, superflousInventory);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Map<Resource, Integer> resourcesToDiscard = new HashMap<>();
        resourcesToDiscard.put(Resource.LUMBER, 2);
        boolean resourcesDiscarded = uut.discardResources(player, resourcesToDiscard);

        Assert.assertFalse(resourcesDiscarded);
    }

    @Test
    public void testWhenPlayerHasTooManyResources_ThenDiscardTooManyReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder()
                .build();
        Inventory superflousInventory = Inventory.builder()
                .addBrick(5)
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, superflousInventory);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Map<Resource, Integer> resourcesToDiscard = new HashMap<>();
        resourcesToDiscard.put(Resource.LUMBER, 2);
        resourcesToDiscard.put(Resource.BRICK, 3);
        boolean resourcesDiscarded = uut.discardResources(player, resourcesToDiscard);

        Assert.assertFalse(resourcesDiscarded);
    }

    @Test
    public void testWhenPlayerHasTooManyResources_ThenDiscardMustMatchPlayerInventory() {
        Dice dice = new RandomDice();
        Board board = Board.builder()
                .build();
        Inventory superflousInventory = Inventory.builder()
                .addBrick(5)
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, superflousInventory);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Map<Resource, Integer> resourcesToDiscard = new HashMap<>();
        resourcesToDiscard.put(Resource.LUMBER, 2);
        resourcesToDiscard.put(Resource.ORE, 2);
        boolean resourcesDiscarded = uut.discardResources(player, resourcesToDiscard);

        Assert.assertFalse(resourcesDiscarded);
    }

    @Test
    public void testWhenMovingRobberToCoordinateNotOnBoard_ThenFalseReturned() {
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Inventory inventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Game game = new Game(board, List.of(player), dice);


        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Coordinate coordinateNotOnBoard = Coordinate.of(0, 1);
        boolean robberMoved = uut.moveRobber(player, coordinateNotOnBoard);

        Assert.assertFalse(robberMoved);
    }

    @Test
    public void testWhenMovingRobberToCoordinateOnBoard_ThenTrueReturned() {
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Inventory inventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Game game = new Game(board, List.of(player), dice);


        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean robberMoved = uut.moveRobber(player, coordinate);

        Assert.assertTrue(robberMoved);
        Assert.assertTrue(game.getState() instanceof TradingPhase);
    }

    @Test
    public void testWhenMovingRobberToSameCoordinateOnBoard_ThenFalseReturned() {
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .setRobber(coordinate)
                .build();
        Inventory inventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Game game = new Game(board, List.of(player), dice);


        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean robberMoved = uut.moveRobber(player, coordinate);

        Assert.assertFalse(robberMoved);
    }

    @Test
    public void testWhenDiscardingCards_ThenPlayerInventoryUpdatedCorrectly() {
        Dice dice = new RandomDice();
        Board board = Board.builder()
                .build();
        Inventory superflousInventory = Inventory.builder()
                .addBrick(5)
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, superflousInventory);
        Game game = new Game(board, List.of(player), dice);

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Map<Resource, Integer> resourcesToDiscard = new HashMap<>();
        resourcesToDiscard.put(Resource.LUMBER, 2);
        resourcesToDiscard.put(Resource.BRICK, 2);
        boolean resourcesDiscarded = uut.discardResources(player, resourcesToDiscard);

        Assert.assertTrue(resourcesDiscarded);
        Assert.assertEquals(3, (int) player.inventory().resources().get(Resource.BRICK));
        Assert.assertEquals(2, (int) player.inventory().resources().get(Resource.LUMBER));
    }
}
