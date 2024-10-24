package com.settlers.game;

import com.settlers.game.states.RollForResources;
import com.settlers.game.states.SetupPhase;
import com.settlers.game.states.State;
import org.junit.Assert;
import org.junit.Test;

public class SetupPhaseTest {
    @Test
    public void testWhenTryingToAddRoadFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean roadAdded = uut.addRoad(player, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirst_ThenTrueReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertTrue(buildingAdded);
    }

    @Test
    public void testWhenTryingToAddTwoSettlementFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean firstBuildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        boolean secondBuildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.FOUR), Building.of(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertTrue(firstBuildingAdded);
        Assert.assertFalse(secondBuildingAdded);
    }

    @Test
    public void testWhenTryingToAddCityFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.CITY));

        Assert.assertFalse(buildingAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirstThenRoad_ThenTrueReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(player, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        Assert.assertTrue(buildingAdded);
        Assert.assertTrue(roadAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirstThenRoadNotAdjacent_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(player, Position.of(coordinate, Direction.FOUR), Road.of(Color.RED));

        Assert.assertTrue(buildingAdded);
        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenSecondRound_ThenPlayersGoInReverse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .addPlayer(orangePlayer)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, false);
        game.setState(uut);
        uut.addBuilding(redPlayer, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(redPlayer, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        Assert.assertEquals(orangePlayer, game.getCurrentPlayer());
    }

    @Test
    public void testWhenSetupPhaseComplete_ThenRollForResourcesState() {
        Player player = Player.create(Color.RED);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        State uut = new SetupPhase(game, true);
        game.setState(uut);
        uut.addBuilding(player, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(player, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));
        // state object changed, get from game
        uut = game.getState();
        uut.addBuilding(player, Position.of(coordinate, Direction.FOUR), Building.of(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(player, Position.of(coordinate, Direction.FOUR), Road.of(Color.RED));

        Assert.assertTrue(game.getState() instanceof RollForResources);
    }

    @Test
    public void testWhenFirstPlayerPlaceFirstSettlementAndRoad_ThenFirstPlayerCantPlaceMore() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, false);
        game.setState(uut);
        uut.addBuilding(redPlayer, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(redPlayer, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        boolean buildingAdded = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(redPlayer, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        Assert.assertFalse(buildingAdded);
        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenRedStarts_BlueCanNotPlaceBuildingOrRoad() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(bluePlayer)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, false);
        game.setState(uut);

        boolean buildingAdded = uut.addBuilding(bluePlayer, Position.of(coordinate, Direction.TWO), Building.of(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(bluePlayer, Position.of(coordinate, Direction.TWO), Road.of(Color.RED));

        Assert.assertFalse(buildingAdded);
        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenPlayersFinishedSetup_ThenStartingResourcesAllocated() {
        Player player = Player.create(Color.BLUE);
        Coordinate lumberCoordinate = Coordinate.of(0,0);
        Tile lumberTile = Tile.builder().build(Resource.LUMBER, 2);

        Coordinate oreCoordinate = Coordinate.of(0,-1);
        Tile oreTile = Tile.builder().build(Resource.ORE, 4);

        Coordinate brickCoordinate = Coordinate.of(1,-1);
        Tile brickTile = Tile.builder().build(Resource.BRICK, 6);

        Board board = Board.builder()
                .addTile(oreCoordinate, oreTile)
                .addTile(lumberCoordinate, lumberTile)
                .addTile(brickCoordinate, brickTile)
                .build();

        Game game = Game.builder()
                .addPlayer(player)
                .setBoard(board)
                .build();

        SetupPhase uut = new SetupPhase(game, false);
        game.setState(uut);
        uut.addBuilding(player, Position.of(lumberCoordinate, Direction.ONE), Building.of(Color.BLUE, Building.Type.SETTLEMENT));
        uut.addRoad(player, Position.of(lumberCoordinate, Direction.ONE), Road.of(Color.BLUE));

        Assert.assertEquals(1, player.inventory().getResource(Resource.LUMBER));
        Assert.assertEquals(1, player.inventory().getResource(Resource.ORE));
        Assert.assertEquals(1, player.inventory().getResource(Resource.BRICK));
    }
}
