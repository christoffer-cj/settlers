package com.settlers.game;

import com.settlers.game.dice.TestingDice;
import com.settlers.game.states.DetermineStartingPlayer;
import com.settlers.game.states.SetupPhase;
import org.junit.Assert;
import org.junit.Test;

public class DetermineStartingPlayerTest {
    @Test
    public void testWhenStartingPlayerDeterminedThenSetupPhaseStateAndStartingPlayerStarts() {
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(whitePlayer)
                .setDice(TestingDice.of(6, 10))
                .build();

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof SetupPhase);
        Assert.assertSame(game.getCurrentPlayer(), whitePlayer);
    }

    @Test
    public void testWhenStartingPlayerNotFound_ThenDetermineStartingPlayerAgain() {
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(whitePlayer)
                .setDice(TestingDice.of(10, 10))
                .build();

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        uut.rollDice(redPlayer);
        uut.rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof DetermineStartingPlayer);
        Assert.assertSame(game.getCurrentPlayer(), redPlayer);
    }

    @Test
    public void testWhenStartingPlayerDeterminedInSecondRound_ThenSetupPhaseAndStartingPlayerStarts() {
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(whitePlayer)
                .setDice(TestingDice.of(2, 2, 5, 10))
                .build();

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
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(whitePlayer)
                .setDice(TestingDice.of(2))
                .build();

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        boolean diceRolled = uut.rollDice(whitePlayer);

        Assert.assertFalse(diceRolled);
    }

    @Test
    public void testWhenRedAndWhiteRollHighestAndBlueLowest_ThenRedAndWhiteBattleForStartingRound() {
        Player redPlayer = Player.create(Color.RED);
        Player whitePlayer = Player.create(Color.WHITE);
        Player bluePlayer = Player.create(Color.BLUE);
        Game game = Game.builder()
                .addPlayer(redPlayer)
                .addPlayer(whitePlayer)
                .addPlayer(bluePlayer)
                .setDice(TestingDice.of(10, 10, 5, 12, 10))
                .build();

        DetermineStartingPlayer uut = new DetermineStartingPlayer(game);
        game.setState(uut);
        game.getState().rollDice(redPlayer);
        game.getState().rollDice(whitePlayer);
        game.getState().rollDice(bluePlayer);
        game.getState().rollDice(redPlayer);
        game.getState().rollDice(whitePlayer);

        Assert.assertTrue(game.getState() instanceof SetupPhase);
        Assert.assertEquals(redPlayer, game.getCurrentPlayer());
    }
}
