package com.settlers.game;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {
    @Test
    public void testWhenCanAffordSettlementButHaveNone_ThenBuySettlementReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addBrick(1)
                .addLumber(1)
                .addGrain(1)
                .addWool(1)
                .setSettlements(0)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean settlementBought = uut.buyBuilding(Building.Type.SETTLEMENT);

        Assert.assertFalse(settlementBought);
    }

    @Test
    public void testWhenCanNotAffordSettlementButHave_ThenBuySettlementReturnFalse() {
        Inventory inventory = Inventory.builder()
                .setSettlements(1)
                .build();
        Player uut = Player.of(Color.BLUE, inventory);
        boolean settlementBought = uut.buyBuilding(Building.Type.SETTLEMENT);

        Assert.assertFalse(settlementBought);
    }

    @Test
    public void testWhenCanAffordSettlementAndHave_ThenBuySettlementReturnTrue() {
        Inventory inventory = Inventory.builder()
                .addBrick(1)
                .addLumber(1)
                .addGrain(1)
                .addWool(1)
                .setSettlements(1)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean settlementBought = uut.buyBuilding(Building.Type.SETTLEMENT);

        Assert.assertTrue(settlementBought);
    }

    @Test
    public void testWhenCanAffordCityButHaveNone_ThenBuyCityReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addOre(3)
                .addGrain(2)
                .setCities(0)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean cityBought = uut.buyBuilding(Building.Type.CITY);

        Assert.assertFalse(cityBought);
    }

    @Test
    public void testWhenCanNotAffordCityButHave_ThenBuyCityReturnFalse() {
        Inventory inventory = Inventory.builder()
                .setCities(1)
                .build();
        Player uut = Player.of(Color.BLUE, inventory);
        boolean cityBought = uut.buyBuilding(Building.Type.CITY);

        Assert.assertFalse(cityBought);
    }

    @Test
    public void testWhenCanAffordCityAndHave_ThenBuyCityReturnTrue() {
        Inventory inventory = Inventory.builder()
                .addOre(3)
                .addGrain(2)
                .setCities(1)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean cityBought = uut.buyBuilding(Building.Type.CITY);

        Assert.assertTrue(cityBought);
    }

    @Test
    public void testWhenCanAffordRoadButHaveNone_ThenBuyRoadReturnFalse() {
        Inventory inventory = Inventory.builder()
                .addBrick(1)
                .addLumber(1)
                .setRoads(0)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean roadBought = uut.buyRoad();

        Assert.assertFalse(roadBought);
    }

    @Test
    public void testWhenCanNotAffordRoadButHave_ThenBuyRoadReturnFalse() {
        Inventory inventory = Inventory.builder()
                .setRoads(1)
                .build();
        Player uut = Player.of(Color.BLUE, inventory);
        boolean roadBought = uut.buyRoad();

        Assert.assertFalse(roadBought);
    }

    @Test
    public void testWhenCanAffordRoadAndHave_ThenBuyRoadReturnTrue() {
        Inventory inventory = Inventory.builder()
                .addBrick(1)
                .addLumber(1)
                .setRoads(1)
                .build();
        Player uut = Player.of(Color.RED, inventory);
        boolean roadBought = uut.buyRoad();

        Assert.assertTrue(roadBought);
    }
}
