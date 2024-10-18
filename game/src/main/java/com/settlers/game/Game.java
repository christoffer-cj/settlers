package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.states.DetermineStartingPlayer;
import com.settlers.game.states.State;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayer = 0;
    private State state;
    private Player largestArmy;
    private Player longestRoad;

    public Game(Board board,
                List<Player> players,
                Dice dice) {
        this.board = Objects.requireNonNull(board);
        this.players = List.copyOf(Objects.requireNonNull(players));
        this.dice = Objects.requireNonNull(dice);
        this.state = new DetermineStartingPlayer(this);
        this.largestArmy = null;
        this.longestRoad = null;
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

    public Player getPlayer(Color color) {
        return players.stream().filter(player -> player.color() == color).findFirst().orElseThrow();
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

    public void assignLargestArmy() {
        bing bong ding dong
    }
}
