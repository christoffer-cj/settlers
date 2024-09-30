package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.impl.TestingDice;
import com.settlers.game.states.impl.MoveRobber;
import com.settlers.game.states.impl.RollForResources;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RollForResourcesTest {
    @Test
    public void testWhenRollForResourcesYieldResourcesForSettlement_ThenPlayerReceivesOneResource() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Direction direction = Direction.THREE;
        Building settlement = Building.builder().build(Color.WHITE, Building.Type.SETTLEMENT);
        Tile tile = Tile.builder()
                .addBuilding(direction, settlement)
                .build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.WHITE);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(1, (int) player.inventory().resources().get(Resource.ORE));
    }

    @Test
    public void testWhenRollForResourcesYieldResourcesForCity_ThenPlayerReceivesTwoResource() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Direction direction = Direction.THREE;
        Building city = Building.builder().build(Color.WHITE, Building.Type.CITY);
        Tile tile = Tile.builder()
                .addBuilding(direction, city)
                .build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.WHITE);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(2, (int) player.inventory().resources().get(Resource.ORE));
    }

    @Test
    public void testWhenRollForResourcesYieldResourcesForCityAndSettlement_ThenPlayerReceivesThreeResource() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Direction cityDirection = Direction.THREE;
        Building city = Building.builder().build(Color.WHITE, Building.Type.CITY);
        Direction settlementDirection = Direction.FIVE;
        Building settlement = Building.builder().build(Color.WHITE, Building.Type.SETTLEMENT);
        Tile tile = Tile.builder()
                .addBuilding(cityDirection, city)
                .addBuilding(settlementDirection, settlement)
                .build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.WHITE);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(3, (int) player.inventory().resources().get(Resource.ORE));
    }

    @Test
    public void testWhenRollForResourcesYieldResourcesForOtherColor_ThenPlayerReceivesNoResources() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Direction direction = Direction.THREE;
        Building settlement = Building.builder().build(Color.RED, Building.Type.SETTLEMENT);
        Tile tile = Tile.builder()
                .addBuilding(direction, settlement)
                .build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.WHITE);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(0, (int) player.inventory().resources().get(Resource.ORE));
    }

    @Test
    public void testWhenRollForResourcesYieldResourcesFromTwoTiles_ThenPlayerReceivesResources() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Coordinate adjacentCoordinate = Coordinate.of(2, 2);
        Direction direction = Direction.THREE;
        Building settlement = Building.builder().build(Color.RED, Building.Type.SETTLEMENT);
        Tile tile = Tile.builder()
                .addBuilding(direction, settlement)
                .build(Resource.ORE, 6);
        Tile adjacentTile = Tile.builder().build(Resource.LUMBER, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .addTile(adjacentCoordinate, adjacentTile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.RED);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(1, (int) player.inventory().resources().get(Resource.ORE));
        Assert.assertEquals(1, (int) player.inventory().resources().get(Resource.LUMBER));
    }

    @Test
    public void testWhenRollForResourcesYieldResourcesFromOneOfTwoTiles_ThenPlayerReceivesOneResource() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Coordinate adjacentCoordinate = Coordinate.of(2, 2);
        Direction direction = Direction.THREE;
        Building settlement = Building.builder().build(Color.RED, Building.Type.SETTLEMENT);
        Tile tile = Tile.builder()
                .addBuilding(direction, settlement)
                .build(Resource.ORE, 7);
        Tile adjacentTile = Tile.builder().build(Resource.LUMBER, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .addTile(adjacentCoordinate, adjacentTile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player player = Player.create(Color.RED);
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(0, (int) player.inventory().resources().get(Resource.ORE));
        Assert.assertEquals(1, (int) player.inventory().resources().get(Resource.LUMBER));
    }

    @Test
    public void testWhenRollSeven_ThenMoveRobberState() {
        Board board = Board.builder().build();
        Player player = Player.create(Color.ORANGE);
        Dice dice = new TestingDice(List.of(7));
        Game game = new Game(board, List.of(player), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertTrue(game.getState() instanceof MoveRobber);
    }

    @Test
    public void testWhenTwoPlayersYieldResources_ThenBothPlayersReceiveResources() {
        Coordinate coordinate = Coordinate.of(1, 2);
        Direction whiteDirection = Direction.THREE;
        Building whiteSettlement = Building.builder().build(Color.WHITE, Building.Type.SETTLEMENT);
        Building redSettlement = Building.builder().build(Color.RED, Building.Type.SETTLEMENT);
        Direction redDirection = Direction.TWO;
        Tile tile = Tile.builder()
                .addBuilding(whiteDirection, whiteSettlement)
                .addBuilding(redDirection, redSettlement)
                .build(Resource.ORE, 6);
        Board board = Board.builder()
                .addTile(coordinate, tile)
                .build();
        Dice dice = new TestingDice(List.of(6));
        Player whitePlayer = Player.create(Color.WHITE);
        Player redPlayer = Player.create(Color.RED);
        Game game = new Game(board, List.of(whitePlayer, redPlayer), dice);

        RollForResources uut = new RollForResources(game);
        game.setState(uut);
        uut.rollDice();

        Assert.assertEquals(1, (int) whitePlayer.inventory().resources().get(Resource.ORE));
        Assert.assertEquals(1, (int) redPlayer.inventory().resources().get(Resource.ORE));
    }
}