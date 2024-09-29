package com.settlers.game;

import com.settlers.game.state.State;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Board board;
    private final List<Player> players;
    private int currentPlayer = 0;
    private State state;

    public Game(Board board, List<Player> players) {
        this.board = Objects.requireNonNull(board);
        Objects.requireNonNull(players);
        this.players = List.copyOf(players);
        assert players.size() >= 2;
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
}
