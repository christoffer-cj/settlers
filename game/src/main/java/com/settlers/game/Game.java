package com.settlers.game;

import com.settlers.game.dice.Dice;
import com.settlers.game.dice.RandomDice;
import com.settlers.game.states.DetermineStartingPlayer;
import com.settlers.game.states.State;

import java.util.*;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final Queue<DevelopmentCard> developmentCards;
    private int currentPlayer = 0;
    private State state;
    private Player largestArmy;
    private Player longestRoad;

    public Game(Board board,
                List<Player> players,
                Dice dice,
                Queue<DevelopmentCard> developmentCards) {
        this.board = Objects.requireNonNull(board);
        this.players = List.copyOf(Objects.requireNonNull(players));
        this.dice = Objects.requireNonNull(dice);
        this.developmentCards = new LinkedList<>(developmentCards);
        this.state = new DetermineStartingPlayer(this);
        this.largestArmy = null;
        this.longestRoad = null;
    }

    public static Builder builder() {
        return new Builder();
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
        Collection<Player> winners = new ArrayList<>();
        for (Player player : getPlayers()) {
            int points = 0;
            points += player.inventory().victoryPoints();
            if (player == largestArmy) points += 2;
            if (player == longestRoad) points += 2;
            points += getBoard().getBuildings(player.color())
                    .stream()
                    .map(Building::type)
                    .map(Building.Type::resources)
                    .reduce(0, Integer::sum);
            if (points >= 10) winners.add(player);
        }

        if (winners.contains(getCurrentPlayer())) {
            return Optional.of(getCurrentPlayer());
        } else {
            return Optional.empty();
        }
    }

    public boolean hasDevelopmentCards() {
        return !developmentCards.isEmpty();
    }

    public DevelopmentCard takeDevelopmentCard() {
        if (!hasDevelopmentCards()) {
            return null;
        }

        return developmentCards.poll();
    }

    public static final class Builder {
        private Board board = Board.builder().build();
        private List<Player> players = new ArrayList<>();
        private Dice dice = RandomDice.create();
        private Queue<DevelopmentCard> developmentCards = new LinkedList<>();

        private Builder() {}

        public Game build() {
            return new Game(board, players, dice, developmentCards);
        }

        public Builder setBoard(Board board) {
            assert board != null;
            this.board = board;
            return this;
        };

        public Builder addPlayer(Player player) {
            if (players.contains(player)) return this;
            players.add(player);
            return this;
        }

        public Builder setDice(Dice dice) {
            assert dice != null;
            this.dice = dice;
            return this;
        }

        public Builder setDevelopmentCards(List<DevelopmentCard> developmentCards) {
            assert developmentCards != null;
            this.developmentCards.addAll(developmentCards);
            return this;
        }
    }
}
