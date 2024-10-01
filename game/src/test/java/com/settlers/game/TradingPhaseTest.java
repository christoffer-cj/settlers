package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.TradingPhase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TradingPhaseTest {
    @Test
    public void testWhenOfferMadeOnAnotherPlayersBehalf_ThenReturnFalse() {
        Dice dice = new RandomDice();
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player bluePlayer = Player.create(Color.BLUE);
        Player orangePlayer = Player.create(Color.ORANGE);
        Game game = new Game(board, List.of(redPlayer, bluePlayer, orangePlayer), dice);

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeAccepted = uut.acceptTrade(bluePlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertTrue(tradeAccepted);
        Assert.assertEquals(4, (int) redInventory.resources().get(Resource.LUMBER));
        Assert.assertEquals(1, (int) blueInventory.resources().get(Resource.BRICK));
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

        TradingPhase uut = new TradingPhase(game);
        game.setState(uut);
        Trade trade = Trade.builder()
                .offerBrick(1)
                .receiveLumber(4)
                .build(redPlayer, bluePlayer);
        boolean tradeOffered = uut.offerTrade(redPlayer, trade);
        boolean tradeAccepted = uut.declineTrade(bluePlayer);

        Assert.assertTrue(tradeOffered);
        Assert.assertTrue(tradeAccepted);
        Assert.assertEquals(4, (int) blueInventory.resources().get(Resource.LUMBER));
        Assert.assertEquals(1, (int) redInventory.resources().get(Resource.BRICK));
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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

        TradingPhase uut = new TradingPhase(game);
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
}
