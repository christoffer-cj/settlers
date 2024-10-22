package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.states.DetermineStartingPlayer;
import com.settlers.game.states.State;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        int largestArmy = 0;
        for (Player player : getPlayers()) {
            if (player.inventory().usedKnights() > largestArmy) largestArmy = player.inventory().usedKnights();
        }
        if (largestArmy < 3) return;
        Player playerWithLargestArmy = null;
        for (Player player : getPlayers()) {
            if (player.inventory().usedKnights() == largestArmy) {
                if (playerWithLargestArmy == null) {
                    playerWithLargestArmy = player;
                } else {
                    // tie for largest army, original player keeps it
                    return;
                }
            }
        }
        this.largestArmy = playerWithLargestArmy;
    }

    public void assignLongestRoad() {
        int longestRoad = 0;
        for (Player player : getPlayers()) {
            if (getBoard().getLongestRoad(player) > longestRoad) longestRoad = getBoard().getLongestRoad(player);
        }
        if (longestRoad < 5) return;
        Player playerWithLongestRoad = null;
        for (Player player : getPlayers()) {
            if (getBoard().getLongestRoad(player) == longestRoad) {
                if (playerWithLongestRoad  == null) {
                    playerWithLongestRoad = player;
                } else {
                    // tie for largest army, original player keeps it
                    return;
                }
            }
        }
        this.longestRoad = playerWithLongestRoad;
    }

    public Optional<Player> getWinner() {
        for (Player player : getPlayers()) {
            int points = 0;

        }

        return Optional.empty();
    }
}
