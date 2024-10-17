package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.BuildingPhase;
import com.settlers.game.states.RollForResources;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BuildingPhaseTest {
    @Test
    public void testWhenAddBuildingByNotCurrentPlayer_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addBuilding(bluePlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenAddBuildingOfWrongColor_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenAddCityNotOnSettlement_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.RED, Building.Type.CITY));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenAddCityOnSettlement_ThenReturnTrue() {
        Inventory redInventory = Inventory.builder()
                .addGrain(2)
                .addOre(3)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder()
                .addRoad(Direction.FIVE, Road.builder().build(Color.RED))
                .addBuilding(Direction.FIVE, Building.builder().build(Color.RED, Building.Type.SETTLEMENT))
                .build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.RED, Building.Type.CITY));

        Assert.assertTrue(buildingPlaced);
    }

    @Test
    public void testWhenAddSettlementNotNextToRoad_ThenReturnFalse() {
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .addGrain(1)
                .addWool(1)
                .addLumber(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);
        boolean buildingPlaced = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenAddSettlementNextToRoad_ThenReturnTrue() {
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .addGrain(1)
                .addWool(1)
                .addLumber(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder()
                .addRoad(Direction.FIVE, Road.builder().build(Color.RED))
                .build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);
        boolean buildingPlaced = uut.addBuilding(redPlayer, Position.of(coordinate, Direction.FIVE), Building.builder().build(Color.RED, Building.Type.SETTLEMENT));

        Assert.assertTrue(buildingPlaced);
    }

    @Test
    public void testWhenAddRoadByNotCurrentPlayer_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addRoad(bluePlayer, Position.of(coordinate, Direction.FIVE), Road.builder().build(Color.BLUE));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenAddRoadOfWrongColor_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Coordinate coordinate = Coordinate.of(420, 69);
        Tile tile = Tile.builder().build(Resource.LUMBER, 4);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean buildingPlaced = uut.addRoad(redPlayer, Position.of(coordinate, Direction.FIVE), Road.builder().build(Color.BLUE));

        Assert.assertFalse(buildingPlaced);
    }

    @Test
    public void testWhenEndTurn_ThenNextPlayer() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);
        uut.endTurn(redPlayer);

        Assert.assertEquals(bluePlayer, game.getCurrentPlayer());
        Assert.assertTrue(game.getState() instanceof RollForResources);
    }

    @Test
    public void testWhenEndTurnNotByCurrentPlayer_ThenReturnFalse() {
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Game game = new Game(board, List.of(redPlayer, bluePlayer), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);
        boolean turnEnded = uut.endTurn(bluePlayer);

        Assert.assertFalse(turnEnded);
    }
}
