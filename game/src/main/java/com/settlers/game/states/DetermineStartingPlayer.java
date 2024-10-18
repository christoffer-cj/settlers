package com.settlers.game.states;

import com.settlers.game.Game;
import com.settlers.game.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DetermineStartingPlayer extends AbstractState {
    private final Map<Player, Integer> playerRolls = new HashMap<>();

    public DetermineStartingPlayer(Game game) {
        super(game);
    }

    @Override
    public boolean rollDice(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;
        int roll = game.getDice().roll();
        playerRolls.put(game.getCurrentPlayer(), roll);

        if (playerRolls.size() != game.getPlayers().size()) {
            game.nextPlayer();
            return true;
        }

        Optional<Player> winner = getWinner();
        if (winner.isEmpty()) {
            playerRolls.clear();
            game.nextPlayer();
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
}
