package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.TestingDice;
import com.settlers.game.states.DetermineStartingPlayer;
import com.settlers.game.states.SetupPhase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DetermineStartingPlayerTest {
    @Test
    public void testWhenStartingPlayerDeterminedThenSetupPhaseOneStateAndStartingPlayerStarts() {
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        List<Player> players = List.of(redPlayer, whitePlayer);
        Dice dice = new TestingDice(List.of(5, 10));
        Game game = new Game(board, players, dice);

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof SetupPhase);
        Assert.assertSame(game.getCurrentPlayer(), whitePlayer);
    }

    @Test
    public void testWhenStartingPlayerNotFound_ThenDetermineStartingPlayerAgain() {
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        List<Player> players = List.of(redPlayer, whitePlayer);
        Dice dice = new TestingDice(List.of(10, 10));
        Game game = new Game(board, players, dice);

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof DetermineStartingPlayer);
        Assert.assertSame(game.getCurrentPlayer(), redPlayer);
    }

    @Test
    public void testWhenStartingPlayerDeterminedInSecondRound_ThenSetupPhaseOneAndStartingPlayerStarts() {
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        List<Player> players = List.of(redPlayer, whitePlayer);
        Dice dice = new TestingDice(List.of(2, 2, 5, 10));

        Game game = new Game(board, players, dice);
        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof SetupPhase);
        Assert.assertSame(game.getCurrentPlayer(), whitePlayer);
    }

    @Test
    public void testWhenRedStarts_ThenWhiteCanNotRollFirst() {
        Board board = Board.builder().build();
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        List<Player> players = List.of(redPlayer, whitePlayer);
        Dice dice = new TestingDice(List.of(2));

        Game game = new Game(board, players, dice);
        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        boolean diceRolled = uut.rollDice(whitePlayer);

        Assert.assertFalse(diceRolled);
    }
}
