package com.settlers.game;

import com.settlers.game.states.ActionPhase;
import com.settlers.game.states.MoveRobber;
import org.junit.Assert;
import org.junit.Test;

public class MoveRobberTest {
    @Test
    public void testWhenMovingRobberToCoordinateNotOnBoard_ThenFalseReturned() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Inventory inventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();


        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        Coordinate coordinateNotOnBoard = Coordinate.of(0, 1);
        boolean robberMoved = uut.moveRobber(player, coordinateNotOnBoard);

        Assert.assertFalse(robberMoved);
    }

    @Test
    public void testWhenMovingRobberToCoordinateOnBoard_ThenTrueReturned() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Tile tile = Tile.builder().build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Inventory inventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean robberMoved = uut.moveRobber(player, coordinate);

        Assert.assertTrue(robberMoved);
        Assert.assertTrue(game.getState() instanceof ActionPhase);
    }

    @Test
    public void testWhenMovingRobberToSameCoordinateOnBoard_ThenFalseReturned() {
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
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        boolean robberMoved = uut.moveRobber(player, coordinate);

        Assert.assertFalse(robberMoved);
    }

    @Test
    public void testWhenRobberMovedToTileWithNoAdjacentBuildings_ThenNoOneToStealFrom() {
        Player player = Player.create(Color.RED);
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder().build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(player, coordinate);

        Assert.assertTrue(game.getState() instanceof ActionPhase);
    }

    @Test
    public void testWhenRobberMovedToTileWithAdjacentBuildings_ThenPeopleToStealFrom() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.of(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .setBoard(board)
                .build();

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(redPlayer, coordinate);

        Assert.assertFalse(game.getState() instanceof ActionPhase);
    }

    @Test
    public void testWhenStealingFromPlayerWithNoResources_ThenNoInventoryUpdated() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.of(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .setBoard(board)
                .build();

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
        Coordinate coordinate = Coordinate.of(10, 10);
        Tile tile = Tile.builder()
                .addBuilding(Direction.ONE, Building.of(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .setBoard(board)
                .build();

        MoveRobber uut = new MoveRobber(game);
        game.setState(uut);
        uut.moveRobber(redPlayer, coordinate);
        boolean resourceStolen = uut.stealResource(redPlayer, bluePlayer);

        Assert.assertTrue(resourceStolen);
        Assert.assertEquals(1, redPlayer.inventory().getResource(Resource.BRICK));
        Assert.assertEquals(2, bluePlayer.inventory().getResource(Resource.BRICK));
    }
}
