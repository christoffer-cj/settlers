package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;

import java.util.*;

public class DetermineStartingPlayer extends AbstractState {
    private final Map<Player, Integer> playerRolls = new HashMap<>();
    private List<Player> playersInRound;

    public DetermineStartingPlayer(Game game) {
        super(game);
        playersInRound = game.getPlayers();
    }

    @Override
    public boolean rollDice(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;
        int roll = game.getDice().roll();
        playerRolls.put(game.getCurrentPlayer(), roll);

        if (playerRolls.size() != playersInRound.size()) {
            do game.nextPlayer();
            while (!playersInRound.contains(game.getCurrentPlayer()));
            return true;
        }

        Optional<Player> winner = getWinner();
        if (winner.isEmpty()) {
            playersInRound = findPlayersForRound();
            playerRolls.clear();
            do game.nextPlayer();
            while (!playersInRound.contains(game.getCurrentPlayer()));
            return true;
        }
        while (!game.getCurrentPlayer().equals(winner.get())) {
            game.nextPlayer();
        }
        game.setState(new SetupPhase(game, true));

        return true;
    }

    private Optional<Player> getWinner() {
        int maxRoll = playerRolls.values().stream().max(Integer::compareTo).orElseThrow();
        if (playerRolls.values().stream().filter(roll -> roll == maxRoll).toList().size() != 1) {
            // multiple players rolled the same max roll, no winner
            return Optional.empty();
        }

        Player winner = playerRolls.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(maxRoll))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

        return Optional.of(winner);
    }

    private List<Player> findPlayersForRound() {
        int maxRoll = playerRolls.values().stream().max(Integer::compareTo).orElseThrow();
        List<Player> playersInNewRound = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : playerRolls.entrySet()) {
            if (entry.getValue() == maxRoll) {
                playersInNewRound.add(entry.getKey());
            }
        }

        return playersInNewRound;
    }
}
