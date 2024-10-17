package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.DiscardResources;
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

        DiscardResources uut = new DiscardResources(game);
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

        DiscardResources uut = new DiscardResources(game);
        game.setState(uut);
        Map<Resource, Integer> resourcesToDiscard = new HashMap<>();
        resourcesToDiscard.put(Resource.LUMBER, 2);
        resourcesToDiscard.put(Resource.BRICK, 2);
        boolean resourcesDiscarded = uut.discardResources(player, resourcesToDiscard);

        Assert.assertTrue(resourcesDiscarded);
        Assert.assertEquals(3, player.inventory().getResource(Resource.BRICK));
        Assert.assertEquals(2, player.inventory().getResource(Resource.LUMBER));
    }

    @Test
    public void testWhenRobberMovedToTileWithNoAdjacentBuildings_ThenNoOneToStealFrom() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder().build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);
        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(player, coordinate);

        Assert.assertTrue(game.getState() instanceof TradingPhase);
    }

    @Test
    public void testWhenRobberMovedToTileWithAdjacentBuildings_ThenPeopleToStealFrom() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);
        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(redPlayer, coordinate);

        Assert.assertFalse(game.getState() instanceof TradingPhase);
    }

    @Test
    public void testWhenStealingFromPlayerWithNoResources_ThenNoInventoryUpdated() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);
        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(redPlayer, coordinate);
        boolean resourceStolen = uut.stealResource(redPlayer, bluePlayer);

        Assert.assertTrue(resourceStolen);
        for (Resource resource : Resource.values()) {
            if (resource == Resource.NOTHING) continue;
            Assert.assertEquals(0, redPlayer.inventory().getResource(resource));
        }
        for (Resource resource : Resource.values()) {
            if (resource == Resource.NOTHING) continue;
            Assert.assertEquals(0, bluePlayer.inventory().getResource(resource));
        }
    }

    @Test
    public void whenStealingFromPlayerWithResources_ThenInventoriesUpdatedCorrectly() {
        Player redPlayer = Player.create(Color.RED);
        Inventory blueInventory = Inventory.builder()
                .addBrick(3)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);
        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(redPlayer, coordinate);
        boolean resourceStolen = uut.stealResource(redPlayer, bluePlayer);

        Assert.assertTrue(resourceStolen);
        Assert.assertEquals(1, redPlayer.inventory().getResource(Resource.BRICK));
        Assert.assertEquals(2, bluePlayer.inventory().getResource(Resource.BRICK));
    }
}
