package com.settlers.game.states;

import com.settlers.game.*;

public class BuildingPhase extends BaseState {
    private boolean hasUsedDevelopmentCard;

    public BuildingPhase(Game game, boolean hasUsedDevelopmentCard) {
        super(game);
        this.hasUsedDevelopmentCard = hasUsedDevelopmentCard;
    }

    @Override
    public boolean addBuilding(Player player, Position position, Building building) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(building.color())) return false;

        if (!game.getPlayer(player.color()).canAffordBuilding(building.type())) return false;

        if (!game.getBoard().addBuilding(position, building)) return false;
        game.getPlayer(player.color()).buyBuilding(building.type());

        return true;
    }

    @Override
    public boolean addRoad(Player player, Position position, Road road) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (!player.color().equals(road.color())) return false;

        if (!game.getPlayer(player.color()).canAffordRoad()) return false;

        if (!game.getBoard().addRoad(position, road)) return false;
        game.getPlayer(player.color()).buyRoad();

        return true;
    }

    @Override
    public boolean endTurn(Player player) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        game.nextPlayer();
        game.setState(new RollForResources(game));

        return true;
    }

    @Override
    public boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (hasUsedDevelopmentCard) return false;

        if (!game.getPlayer(player.color()).inventory().useDevelopmentCard(developmentCard)) return false;

        switch (developmentCard) {
            case MONOPOLY -> game.setState(new Monopoly(game, this));
            case ROAD_BUILDING -> game.setState(new RoadBuilding(game, this));
            case YEAR_OF_PLENTY -> game.setState(new YearOfPlenty(game, this));
        }

        return true;
    }

    @Override
    public boolean exchange(Player player, Resource offer, Resource receive) {
        if (!game.getCurrentPlayer().equals(player)) return false;
        if (offer == Resource.NOTHING || receive == Resource.NOTHING) return false;

        int amountNeeded = 4;
        if (game.getBoard().hasHarbor(player, Harbor.ANY)) amountNeeded = 3;
        Harbor resourceHarbor = switch (offer) {
           case BRICK ->  Harbor.BRICK;
           case LUMBER ->  Harbor.LUMBER;
           case ORE ->  Harbor.ORE;
           case GRAIN ->  Harbor.GRAIN;
           case WOOL ->  Harbor.WOOL;
           default ->  null; // dummy case to make compiler happy
       };
       if (game.getBoard().hasHarbor(player, resourceHarbor)) amountNeeded = 2;

       if (!(game.getPlayer(player.color()).inventory().getResource(offer) >= amountNeeded)) return false;

       game.getPlayer(player.color()).inventory().putResource(receive, 1);
       game.getPlayer(player.color()).inventory().putResource(offer, amountNeeded * -1);

       return true;
    }
}
