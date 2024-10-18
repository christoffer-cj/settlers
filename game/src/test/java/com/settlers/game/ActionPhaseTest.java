package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.ActionPhase;
import com.settlers.game.states.RollForResources;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ActionPhaseTest {
    @Test
    public void testWhenOfferMadeOnAnotherPlayersBehalf_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder().build(bluePlayer, orangePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertFalse(tradeOffered);
    }

    @Test
    public void testWhenOfferMadeByNotCurrentPlayer_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder().build(bluePlayer, orangePlayer);
        boolean tradeOffered = uut.offerTrade(bluePlayer, trade);

        Assert.assertFalse(tradeOffered);
    }

    @Test
    public void testWhenTryingToTradeWithOneself_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder().build(redPlayer, redPlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertFalse(tradeOffered);
    }

    @Test
    public void testWhenOfferingInvalidOffer_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(2)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertFalse(tradeOffered);
    }

    @Test
    public void testWhenOfferingInvalidReceive_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(5)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertFalse(tradeOffered);
    }

    @Test
    public void testWhenValidTradeOffer_ThenReturnTrue() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertTrue(tradeOffered);
    }

    @Test
    public void testWhenTradeInProgressAndAnotherTradeOffered_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean firstTradeOffered = uut.offerTrade(redPlayer, trade);
        boolean secondTradeOffered = uut.offerTrade(redPlayer, trade);

        Assert.assertTrue(firstTradeOffered);
        Assert.assertFalse(secondTradeOffered);
    }

    @Test
    public void testWhenTradeOfferAccepted_ThenReturnTrue() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeAccepted = uut.acceptTrade(bluePlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertTrue(tradeAccepted);
        Assert.assertEquals(4, redInventory.getResource(Resource.LUMBER));
        Assert.assertEquals(1, blueInventory.getResource(Resource.BRICK));
    }

    @Test
    public void testWhenTradeOfferDeclined_ThenReturnTrue() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeAccepted = uut.declineTrade(bluePlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertTrue(tradeAccepted);
        Assert.assertEquals(4, blueInventory.getResource(Resource.LUMBER));
        Assert.assertEquals(1, redInventory.getResource(Resource.BRICK));
    }

    @Test
    public void testWhenTradeOfferAcceptedByWrongPlayer_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeAccepted = uut.acceptTrade(redPlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertFalse(tradeAccepted);
    }

    @Test
    public void testWhenTradeOfferDeclinedByWrongPlayer_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeDeclined = uut.declineTrade(redPlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertFalse(tradeDeclined);
    }

    @Test
    public void testWhenTradeOfferAcceptedAndSecondTradeOffer_ThenReturnTrue() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade firstTrade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean firstTradeOffered = uut.offerTrade(redPlayer, firstTrade);
        boolean tradeAccepted = uut.acceptTrade(bluePlayer);
        Trade secondTrade = Trade.builder()
                .offerLumber(2)
                .receiveBrick(1)
                .build(redPlayer, bluePlayer);
        boolean secondTradeOffered = uut.offerTrade(redPlayer, secondTrade);

        Assert.assertTrue(firstTradeOffered);
        Assert.assertTrue(tradeAccepted);
        Assert.assertTrue(secondTradeOffered);
    }

    @Test
    public void testWhenTradeOfferDeclinedAndSecondTradeOffer_ThenReturnTrue() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Inventory redInventory = Inventory.builder()
                .addBrick(1)
                .build();
        Player redPlayer = Player.of(Color.RED, redInventory);
        Inventory blueInventory = Inventory.builder()
                .addLumber(4)
                .build();
        Player bluePlayer = Player.of(Color.BLUE, blueInventory);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        Trade firstTrade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean firstTradeOffered = uut.offerTrade(redPlayer, firstTrade);
        boolean tradeDeclined = uut.declineTrade(bluePlayer);
        Trade secondTrade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(2)
                .build(redPlayer, bluePlayer);
        boolean secondTradeOffered = uut.offerTrade(redPlayer, secondTrade);

        Assert.assertTrue(firstTradeOffered);
        Assert.assertTrue(tradeDeclined);
        Assert.assertTrue(secondTradeOffered);
    }

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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);
        boolean turnEnded = uut.endTurn(bluePlayer);

        Assert.assertFalse(turnEnded);
    }

    @Test
    public void testWhenInsufficientResource_ThenReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addBrick(3)
                .build();
        Player player = Player.of(Color.RED, inventory);
        Board board = Board.builder().build();
        Dice dice = new RandomDice();
        Game game = new Game(board, List.of(player), dice);

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
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

        ActionPhase uut = new ActionPhase(game);
        game.setState(uut);

        boolean exchanged = uut.exchange(player, Resource.BRICK, Resource.LUMBER);

        Assert.assertFalse(exchanged);
    }
}
