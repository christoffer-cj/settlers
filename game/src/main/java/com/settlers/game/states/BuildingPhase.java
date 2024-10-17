package com.settlers.game.states;

import com.settlers.game.*;

import java.util.List;

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

        switch (building.type()) {
            case SETTLEMENT -> {
                boolean hasEnoughBrick = game.getPlayer(player.color()).inventory().getResource(Resource.BRICK) >= 1;
                boolean hasEnoughGrain = game.getPlayer(player.color()).inventory().getResource(Resource.GRAIN) >= 1;
                boolean hasEnoughWool = game.getPlayer(player.color()).inventory().getResource(Resource.WOOL) >= 1;
                boolean hasEnoughLumber = game.getPlayer(player.color()).inventory().getResource(Resource.LUMBER) >= 1;

                boolean hasEnoughResources = hasEnoughBrick && hasEnoughGrain && hasEnoughWool && hasEnoughLumber;
                if (!hasEnoughResources) return false;

                if (!game.getBoard().addBuilding(position, building)) return false;

                game.getPlayer(player.color()).inventory().putResource(Resource.BRICK, -1);
                game.getPlayer(player.color()).inventory().putResource(Resource.GRAIN, -1);
                game.getPlayer(player.color()).inventory().putResource(Resource.WOOL, -1);
                game.getPlayer(player.color()).inventory().putResource(Resource.LUMBER, -1);

                return true;
            }
            case CITY -> {
                boolean hasEnoughGrain = game.getPlayer(player.color()).inventory().getResource(Resource.GRAIN) >= 2;
                boolean hasEnoughOre = game.getPlayer(player.color()).inventory().getResource(Resource.ORE) >= 3;

                boolean hasEnoughResources = hasEnoughGrain && hasEnoughOre;
                if (!hasEnoughResources) return false;

                if (!game.getBoard().addBuilding(position, building)) return false;

                game.getPlayer(player.color()).inventory().putResource(Resource.GRAIN, -2);
                game.getPlayer(player.color()).inventory().putResource(Resource.ORE, -3);

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

    @Override
    public boolean useDevelopmentCard(Player player, DevelopmentCard developmentCard) {
        if (!game.getCurrentPlayer().equals(player)) return false;

        if (hasUsedDevelopmentCard) return false;

        if ((game.getPlayer(player.color()).inventory().developmentCards().get(developmentCard) == 0)) return false;

        if (!List.of(DevelopmentCard.MONOPOLY, DevelopmentCard.KNIGHT,  DevelopmentCard.ROAD_BUILDING, DevelopmentCard.YEAR_OF_PLENTY).contains(developmentCard)) return false;

        game.getPlayer(player.color()).inventory().developmentCards().merge(developmentCard, -1, Integer::sum);

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
           default ->  Harbor.ANY; // dummy case to make compiler happy
       };
       if (game.getBoard().hasHarbor(player, resourceHarbor)) amountNeeded = 2;

       if (!(game.getPlayer(player.color()).inventory().resources().get(offer) >= amountNeeded)) return false;

       game.getPlayer(player.color()).inventory().resources().merge(receive, 1, Integer::sum);
       game.getPlayer(player.color()).inventory().resources().merge(offer, amountNeeded * -1, Integer::sum);

       return true;
    }
}
