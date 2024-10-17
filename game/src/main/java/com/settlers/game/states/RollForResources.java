package com.settlers.game.states;

import com.settlers.game.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RollForResources extends BaseState {
    public RollForResources(Game game) {
        super(game);
    }

    @Override
    public boolean rollDice(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        int roll = game.getDice().roll();

        if (roll == 7) {
            game.setState(new DiscardResources(game));
            return true;
        }

        Collection<Coordinate> coordinatesForRoll = new ArrayList<>(game.getBoard().getCoordinates(roll));
        coordinatesForRoll.removeIf(coordinate -> coordinate.equals(game.getBoard().getRobber()));
        for (Player p : game.getPlayers()) {
            Map<Resource, Integer> newResources = new HashMap<>();
            for (Coordinate coordinate : coordinatesForRoll) {
                Resource resource = game.getBoard().getTile(coordinate).orElseThrow().resource();
                Collection<Building> buildings = game.getBoard().getBuildings(coordinate, p.color());
                int amount = buildings.stream()
                        .map(Building::type)
                        .map(Building.Type::resources)
                        .reduce(0, Integer::sum);
                newResources.put(resource, amount);
            }
            for (Map.Entry<Resource, Integer> entry : newResources.entrySet()) {
                p.inventory().putResource(entry.getKey(), entry.getValue());
            }
        }
        game.setState(new TradingPhase(game));

        return true;
    }
}
