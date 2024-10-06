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

        switch (building.type()) {
            case SETTLEMENT -> {
                boolean hasEnoughBrick = game.getPlayer(player.color()).inventory().resources().get(Resource.BRICK) >= 1;
                boolean hasEnoughGrain = game.getPlayer(player.color()).inventory().resources().get(Resource.GRAIN) >= 1;
                boolean hasEnoughWool = game.getPlayer(player.color()).inventory().resources().get(Resource.WOOL) >= 1;
                boolean hasEnoughLumber = game.getPlayer(player.color()).inventory().resources().get(Resource.LUMBER) >= 1;

                boolean hasEnoughResources = hasEnoughBrick && hasEnoughGrain && hasEnoughWool && hasEnoughLumber;
                if (!hasEnoughResources) return false;

                if (!game.getBoard().addBuilding(position, building)) return false;

                game.getPlayer(player.color()).inventory().resources().merge(Resource.BRICK, -1, Integer::sum);
                game.getPlayer(player.color()).inventory().resources().merge(Resource.GRAIN, -1, Integer::sum);
                game.getPlayer(player.color()).inventory().resources().merge(Resource.WOOL, -1, Integer::sum);
                game.getPlayer(player.color()).inventory().resources().merge(Resource.LUMBER, -1, Integer::sum);

                return true;
            }
            case CITY -> {
                boolean hasEnoughGrain = game.getPlayer(player.color()).inventory().resources().get(Resource.GRAIN) >= 2;
                boolean hasEnoughOre = game.getPlayer(player.color()).inventory().resources().get(Resource.ORE) >= 3;

                boolean hasEnoughResources = hasEnoughGrain && hasEnoughOre;
                if (!hasEnoughResources) return false;

                if (!game.getBoard().addBuilding(position, building)) return false;

                game.getPlayer(player.color()).inventory().resources().merge(Resource.GRAIN, -2, Integer::sum);
                game.getPlayer(player.color()).inventory().resources().merge(Resource.ORE, -3, Integer::sum);

                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        boolean hasEnoughBrick = game.getPlayer(player.color()).inventory().resources().get(Resource.BRICK) >= 1;
        boolean hasEnoughLumber = game.getPlayer(player.color()).inventory().resources().get(Resource.LUMBER) >= 1;

        boolean hasEnoughResources = hasEnoughBrick && hasEnoughLumber;
        if (!hasEnoughResources) return false;

        if (!game.getBoard().addRoad(position, road)) return false;

        game.getPlayer(player.color()).inventory().resources().merge(Resource.BRICK, -1, Integer::sum);
        game.getPlayer(player.color()).inventory().resources().merge(Resource.LUMBER, -1, Integer::sum);

        return true;
    }

    @Override
    public boolean endTurn(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        game.nextPlayer();
        game.setState(new RollForResources(game));

        return true;
    }
}
