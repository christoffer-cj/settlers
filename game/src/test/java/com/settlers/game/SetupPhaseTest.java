package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.impl.RandomDice;
import com.settlers.game.states.State;
import com.settlers.game.states.impl.RollForResources;
import com.settlers.game.states.impl.SetupPhase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SetupPhaseTest {
    @Test
    public void testWhenTryingToAddRoadFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean roadAdded = uut.addRoad(Position.of(coordinate, Direction.TWO), Road.builder().build(Color.RED));

        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirst_ThenTrueReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertTrue(buildingAdded);
    }

    @Test
    public void testWhenTryingToTwoSettlementFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean firstBuildingAdded = uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        boolean secondBuildingAdded = uut.addBuilding(Position.of(coordinate, Direction.FOUR), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertTrue(firstBuildingAdded);
        Assert.assertFalse(secondBuildingAdded);
    }

    @Test
    public void testWhenTryingToAddCityFirst_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.CITY));

        Assert.assertFalse(buildingAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirstThenRoad_ThenTrueReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(Position.of(coordinate, Direction.TWO), Road.builder().build(Color.RED));

        Assert.assertTrue(buildingAdded);
        Assert.assertTrue(roadAdded);
    }

    @Test
    public void testWhenTryingToAddSettlementFirstThenRoadNotAdjacent_ThenFalseReturned() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        SetupPhase uut = new SetupPhase(game, true);
        game.setState(uut);
        boolean buildingAdded = uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        boolean roadAdded = uut.addRoad(Position.of(coordinate, Direction.FOUR), Road.builder().build(Color.RED));

        Assert.assertTrue(buildingAdded);
        Assert.assertFalse(roadAdded);
    }

    @Test
    public void testWhenSecondRound_ThenPlayersGoInReverse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        SetupPhase uut = new SetupPhase(game, false);
        game.setState(uut);
        uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(Position.of(coordinate, Direction.TWO), Road.builder().build(Color.RED));

        Assert.assertEquals(orangePlayer, game.getCurrentPlayer());
    }

    @Test
    public void testWhenSetupPhaseComplete_ThenRollForResourcesState() {
        Player player = Player.create(Color.RED);
        Dice dice = new RandomDice();
        Tile tile = Tile.builder().build(Resource.LUMBER, 2);
        Coordinate coordinate = Coordinate.of(2, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(player), dice);

        State uut = new SetupPhase(game, true);
        game.setState(uut);
        uut.addBuilding(Position.of(coordinate, Direction.TWO), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(Position.of(coordinate, Direction.TWO), Road.builder().build(Color.RED));
        // state object changed, get from game
        uut = game.getState();
        uut.addBuilding(Position.of(coordinate, Direction.FOUR), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));
        uut.addRoad(Position.of(coordinate, Direction.FOUR), Road.builder().build(Color.RED));

        Assert.assertTrue(game.getState() instanceof RollForResources);
    }
}
