package com.settlers.game;

import org.junit.Assert;
import org.junit.Test;

public class InventoryTest {
    @Test
    public void testWhenTryingToRemoveMoreResourcesThanInventoryHas_ThenReturnFalse() {
        Inventory uut = Inventory.builder().addBrick(4).build();
        boolean withdrawn = uut.putResource(Resource.BRICK, -5);
        Assert.assertFalse(withdrawn);
        Assert.assertEquals(4, uut.getResource(Resource.BRICK));
    }

    @Test
    public void testWhenTryingToRemoveSufficientResources_ThenReturnTrue() {
        Inventory uut = Inventory.builder().addLumber(4).build();
        boolean withdrawn = uut.putResource(Resource.LUMBER, -3);
        Assert.assertTrue(withdrawn);
        Assert.assertEquals(1, uut.getResource(Resource.LUMBER));
    }

    @Test
    public void testWhenUseBuildingButDoNotHave_ThenReturnFalse() {
        Inventory uut = Inventory.builder().setCities(0).build();
        boolean used = uut.useBuilding(Building.Type.CITY);
        Assert.assertFalse(used);
        Assert.assertEquals(0, uut.getBuilding(Building.Type.CITY));
    }

    @Test
    public void testWhenUseBuildingAndHave_ThenReturnTrue() {
        Inventory uut = Inventory.builder().setSettlements(2).build();
        boolean used = uut.useBuilding(Building.Type.SETTLEMENT);
        Assert.assertTrue(used);
        Assert.assertEquals(1, uut.getBuilding(Building.Type.SETTLEMENT));
    }

    @Test
    public void testWhenUseSettlementFirstThenCityThenSettlement_ThenReturnFalseTrueTrue() {
        Inventory uut = Inventory.builder().setCities(1).setSettlements(0).build();
        boolean firstSettlementUsed = uut.useBuilding(Building.Type.SETTLEMENT);
        boolean cityUsed = uut.useBuilding(Building.Type.CITY);
        boolean secondSettlementUsed = uut.useBuilding(Building.Type.SETTLEMENT);

        Assert.assertFalse(firstSettlementUsed);
        Assert.assertTrue(cityUsed);
        Assert.assertTrue(secondSettlementUsed);
    }
}
