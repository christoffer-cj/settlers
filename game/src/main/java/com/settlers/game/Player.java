package com.settlers.game;

import java.util.Objects;

public record Player(Color color, Inventory inventory) {
    public Player {
        assert color != null;
        assert inventory != null;
    }

    public boolean canAffordBuilding(Building.Type type) {
        return switch (type) {
            case SETTLEMENT ->
                inventory.getResource(Resource.BRICK) >= 1 &&
                inventory.getResource(Resource.WOOL) >= 1 &&
                inventory.getResource(Resource.GRAIN) >= 1 &&
                inventory.getResource(Resource.LUMBER) >= 1;
            case CITY ->
                inventory.getResource(Resource.ORE) >= 3 &&
                inventory.getResource(Resource.GRAIN) >= 2;
        };
    }

    public boolean buyBuilding(Building.Type type) {
        if (!canAffordBuilding(type)) return false;
        if (!inventory.hasBuildings(type)) return false;

        inventory.useBuilding(type);
        switch (type) {
            case SETTLEMENT -> {
                inventory.putResource(Resource.BRICK, -1);
                inventory.putResource(Resource.WOOL, -1);
                inventory.putResource(Resource.GRAIN, -1);
                inventory.putResource(Resource.LUMBER, -1);
            }
            case CITY -> {
                inventory.putResource(Resource.ORE, -3);
                inventory.putResource(Resource.GRAIN, -2);
            }
        }

        return true;
    }

    public boolean canAffordRoad() {
        return inventory.getResource(Resource.LUMBER) >= 1 && inventory.getResource(Resource.BRICK) >= 1;
    }

    public boolean buyRoad() {
        if (!canAffordRoad()) return false;
        if (!inventory.hasRoads()) return false;

        inventory.useRoad();
        inventory.putResource(Resource.LUMBER, -1);
        inventory.putResource(Resource.BRICK, -1);

        return true;
    }

    public boolean canAffordDevelopmentCard() {
        return inventory.getResource(Resource.WOOL) >= 1 &&
                inventory.getResource(Resource.GRAIN) >= 1 &&
                inventory.getResource(Resource.ORE) >= 1;
    }

    public boolean buyDevelopmentCard() {
        if (!canAffordDevelopmentCard()) return false;

        inventory.putResource(Resource.WOOL, -1);
        inventory.putResource(Resource.GRAIN, -1);
        inventory.putResource(Resource.ORE, -1);

        return true;
    }

    public static Player of(Color color, Inventory inventory) {
        return new Player(color, inventory);
    }

    public static Player create(Color color) {
        return new Player(color, Inventory.empty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color);
    }
}
