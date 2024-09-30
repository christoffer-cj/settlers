package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.states.State;
import com.settlers.game.states.impl.DetermineStartingPlayer;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayer = 0;
    private State state;

    public Game(Board board,
                List<Player> players,
                Dice dice) {
        this.board = Objects.requireNonNull(board);
        this.players = List.copyOf(Objects.requireNonNull(players));
        this.dice = Objects.requireNonNull(dice);
        this.state = new DetermineStartingPlayer(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public void previousPlayer() {
        if (currentPlayer == 0) {
            currentPlayer = players.size() - 1;
        } else {
            currentPlayer--;
        }
    }

    public Dice getDice() {
        return dice;
    }
}
