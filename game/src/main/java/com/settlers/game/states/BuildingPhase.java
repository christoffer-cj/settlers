package com.settlers.game.states;

import com.settlers.game.*;

public class BuildingPhase extends BaseState {
    public BuildingPhase(Game game) {
        super(game);
    }

    @Override
    public boolean addBuilding(Player player, Position position, Building building) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(building.color())) return false;

        return false; // todo
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        return super.addRoad(player, position, road);
    }

    @Override
    public boolean endTurn(Player player) {
        return super.endTurn(player);
    }
}
