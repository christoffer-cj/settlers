package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.BuildingPhase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExchangeTest {
    @Test
    public void testWhenInsufficientResource_ThenReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addBrick(3)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Board board = Board.builder().build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertFalse(exchanged);
    }

    @Test
    public void testWhenSufficientResource_ThenReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addBrick(4)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Board board = Board.builder().build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertTrue(exchanged);
        Assert.assertEquals(0, inventory.getResource(Resource.BRICK));
        Assert.assertEquals(1, inventory.getResource(Resource.LUMBER));
    }

    @Test
    public void testWhenPlayerHasAnyHarbor_Then3ResourceSufficient() {
        Inventory inventory = Inventory.builder()
                .addBrick(3)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Tile tile = Tile.builder()
                .addHarbor(Direction.ONE, Harbor.ANY)
                .addBuilding(Direction.ONE, Building.builder().build(Color.RED, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(Coordinate.of(0, 10), tile)
                .build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertTrue(exchanged);
        Assert.assertEquals(0, inventory.getResource(Resource.BRICK));
        Assert.assertEquals(1, inventory.getResource(Resource.LUMBER));
    }

    @Test
    public void testWhenPlayerHasSpecificResourceHarbor_Then2ResourceSufficient() {
        Inventory inventory = Inventory.builder()
                .addBrick(2)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Tile tile = Tile.builder()
                .addHarbor(Direction.ONE, Harbor.BRICK)
                .addBuilding(Direction.ONE, Building.builder().build(Color.RED, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(Coordinate.of(0, 10), tile)
                .build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertTrue(exchanged);
        Assert.assertEquals(0, inventory.getResource(Resource.BRICK));
        Assert.assertEquals(1, inventory.getResource(Resource.LUMBER));
    }

    @Test
    public void testWhenPlayerHasWrongSpecificResourceHarbor_Then2ResourceInsufficient() {
        Inventory inventory = Inventory.builder()
                .addBrick(2)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Tile tile = Tile.builder()
                .addHarbor(Direction.ONE, Harbor.LUMBER)
                .addBuilding(Direction.ONE, Building.builder().build(Color.RED, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(Coordinate.of(0, 10), tile)
                .build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertFalse(exchanged);
    }

    @Test
    public void testWhenOtherPlayerHasSpecificResourceHarbor_Then2ResourceInsufficient() {
        Inventory inventory = Inventory.builder()
                .addBrick(2)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Tile tile = Tile.builder()
                .addHarbor(Direction.ONE, Harbor.BRICK)
                .addBuilding(Direction.ONE, Building.builder().build(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 4);
        Board board = Board.builder()
                .addTile(Coordinate.of(0, 10), tile)
                .build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        BuildingPhase uut = new BuildingPhase(game, false);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertFalse(exchanged);
    }
}
