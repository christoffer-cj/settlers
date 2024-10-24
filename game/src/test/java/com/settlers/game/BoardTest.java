package com.settlers.game;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class BoardTest {

    @Test
    public void whenNoTiles_ThenGetBuildingReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;

        Optional<Building> building = uut.getBuilding(Position.of(coordinate, direction));

        Assert.assertTrue(building.isEmpty());
    }

    @Test
    public void whenTileWithBuilding_ThenGetBuildingReturnBuilding() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(direction, Building.of(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(Position.of(coordinate, direction));

        Assert.assertTrue(building.isPresent());
    }

    @Test
    public void whenTileWithBuildingOnAnotherTile_ThenGetBuildingReturnBuilding() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.FIVE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(direction, Building.of(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);

        Tile tileAdjacentToBuilding = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithBuilding)
                .addTile(adjacentCoordinate, tileAdjacentToBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(Position.of(adjacentCoordinate, adjacentDirection));

        Assert.assertTrue(building.isPresent());
    }

    @Test
    public void whenTileWithBuildingButAskForTileWithNoBuilding_ThenGetBuildingReturnEmpty() {
        Coordinate coordinateWithBuilding = Coordinate.of(0, 0);
        Coordinate coordinateWithoutBuilding = Coordinate.of(420, 69);
        Direction directionWithBuilding = Direction.ONE;
        Direction directionWithoutBuilding = Direction.THREE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(directionWithBuilding, Building.of(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);
        Tile tileWithoutBuilding = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithBuilding, tileWithBuilding)
                .addTile(coordinateWithoutBuilding, tileWithoutBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(Position.of(coordinateWithoutBuilding, directionWithoutBuilding));

        Assert.assertTrue(building.isEmpty());
    }

    @Test
    public void whenNoTiles_ThenGetHarborReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;

        Optional<Harbor> harbor = uut.getHarbor(Position.of(coordinate, direction));

        Assert.assertTrue(harbor.isEmpty());
    }

    @Test
    public void whenTileWithHarbor_ThenGetHarborReturnHarbor() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(direction, Harbor.LUMBER)
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(Position.of(coordinate, direction));

        Assert.assertTrue(harbor.isPresent());
    }

    @Test
    public void whenTileWithHarborOnAnotherTile_ThenGetHarborReturnHarbor() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.FIVE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(direction, Harbor.LUMBER)
                .build(Resource.ORE, 8);

        Tile tileAdjacentToHarbor = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithHarbor)
                .addTile(adjacentCoordinate, tileAdjacentToHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(Position.of(adjacentCoordinate, adjacentDirection));

        Assert.assertTrue(harbor.isPresent());
    }

    @Test
    public void whenTileWithHarborButAskForTileWithNoHarbor_ThenGetHarborReturnEmpty() {
        Coordinate coordinateWithHarbor = Coordinate.of(0, 0);
        Coordinate coordinateWithoutHarbor = Coordinate.of(420, 69);
        Direction directionWithHarbor = Direction.ONE;
        Direction directionWithoutHarbor = Direction.THREE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(directionWithHarbor, Harbor.ORE)
                .build(Resource.ORE, 8);
        Tile tileWithoutHarbor = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithHarbor, tileWithHarbor)
                .addTile(coordinateWithoutHarbor, tileWithoutHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(Position.of(coordinateWithoutHarbor, directionWithoutHarbor));

        Assert.assertTrue(harbor.isEmpty());
    }

    @Test
    public void whenNoTiles_ThenGetRoadReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;

        Optional<Road> road = uut.getRoad(Position.of(coordinate, direction));

        Assert.assertTrue(road.isEmpty());
    }

    @Test
    public void whenTileWithRoad_ThenGetRoadReturnRoad() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tileWithRoad = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithRoad)
                .build();

        Optional<Road> road = uut.getRoad(Position.of(coordinate, direction));

        Assert.assertTrue(road.isPresent());
    }

    @Test
    public void whenTileWithRoadOnAnotherTile_ThenGetRoadReturnRoad() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.FOUR;
        Tile tileWithRoad = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .build(Resource.ORE, 8);

        Tile tileAdjacentToRoad = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithRoad)
                .addTile(adjacentCoordinate, tileAdjacentToRoad)
                .build();

        Optional<Road> road = uut.getRoad(Position.of(adjacentCoordinate, adjacentDirection));

        Assert.assertTrue(road.isPresent());
    }

    @Test
    public void whenTileWithRoadButAskForTileWithNoRoad_ThenGetRoadReturnEmpty() {
        Coordinate coordinateWithRoad = Coordinate.of(0, 0);
        Coordinate coordinateWithoutRoad = Coordinate.of(420, 69);
        Direction directionWithRoad = Direction.ONE;
        Direction directionWithoutRoad = Direction.THREE;
        Tile tileWithRoad = Tile.builder()
                .addRoad(directionWithRoad, Road.of(Color.RED))
                .build(Resource.ORE, 8);
        Tile tileWithoutRoad = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithRoad, tileWithRoad)
                .addTile(coordinateWithoutRoad, tileWithoutRoad)
                .build();

        Optional<Road> road = uut.getRoad(Position.of(coordinateWithoutRoad, directionWithoutRoad));

        Assert.assertTrue(road.isEmpty());
    }

    @Test
    public void whenTileWithNoRoad_ThenAddRoadReturnsFalse() {
        Coordinate coordinate = Coordinate.of(2, 4);
        Direction direction = Direction.FIVE;
        Tile tile = Tile.builder().build(Resource.ORE, 5);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();

        Road roadToAdd = Road.of(Color.ORANGE);

        boolean roadAdded = uut.addRoad(Position.of(coordinate, direction), roadToAdd);

        Assert.assertFalse(roadAdded);
    }

    @Test
    public void whenTileWithRoad_ThenAddRoadReturnsFalse() {
        Coordinate coordinateWithRoad = Coordinate.of(2, 4);
        Direction directionWithRoad = Direction.FIVE;
        Road road = Road.of(Color.ORANGE);
        Tile tile = Tile.builder()
                .addRoad(directionWithRoad, road)
                .build(Resource.ORE, 5);

        Board uut = Board.builder()
                .addTile(coordinateWithRoad, tile)
                .build();

        Road roadToAdd = Road.of(Color.BLUE);

        boolean roadAdded = uut.addRoad(Position.of(coordinateWithRoad, directionWithRoad), roadToAdd);

        Assert.assertFalse(roadAdded);
    }

    @Test
    public void whenTileWithAdjacentRoad_ThenAddRoadReturnsFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.FOUR;
        Tile tileWithRoad = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .build(Resource.ORE, 8);

        Tile tileAdjacentToRoad = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithRoad)
                .addTile(adjacentCoordinate, tileAdjacentToRoad)
                .build();

        Road roadToAdd = Road.of(Color.WHITE);

        boolean roadAdded = uut.addRoad(Position.of(adjacentCoordinate, adjacentDirection), roadToAdd);

        Assert.assertFalse(roadAdded);
    }

    @Test
    public void whenSetupPhase_ThenAddSettlementNotNextToRoadReturnTrue() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder().build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.SETTLEMENT);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement, true);

        Assert.assertTrue(settlementAdded);
    }

    @Test
    public void whenNotSetupPhase_ThenAddSettlementNotNextToRoadReturnFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder().build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.SETTLEMENT);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement, false);

        Assert.assertFalse(settlementAdded);
    }

    @Test
    public void whenAddBuildingNextToRoad_ThenReturnTrue() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.SETTLEMENT);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement);

        Assert.assertTrue(settlementAdded);
    }

    @Test
    public void whenAddBuildingNextToRoadOfWrongColor_ThenReturnFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.BLUE))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.SETTLEMENT);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement);

        Assert.assertFalse(settlementAdded);
    }

    @Test
    public void whenAddBuildingWithAdjacentBuilding_ThenReturnFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.TWO;
        Tile tile = Tile.builder()
                .addBuilding(direction, Building.of(Color.ORANGE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.SETTLEMENT);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, adjacentDirection), settlement);

        Assert.assertFalse(settlementAdded);
    }

    @Test
    public void whenAddCityWithNoSettlement_ThenReturnFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.CITY);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement);

        Assert.assertFalse(settlementAdded);
    }

    @Test
    public void whenAddCityWithSettlement_ThenReturnTrue() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .addBuilding(direction, Building.of(Color.RED, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.CITY);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement);

        Assert.assertTrue(settlementAdded);
    }

    @Test
    public void whenAddCityWithSettlementOfWrongColor_ThenReturnFalse() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.RED))
                .addBuilding(direction, Building.of(Color.BLUE, Building.Type.SETTLEMENT))
                .build(Resource.ORE, 12);
        Building settlement = Building.of(Color.RED, Building.Type.CITY);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();


        boolean settlementAdded = uut.addBuilding(Position.of(coordinate, direction), settlement);

        Assert.assertFalse(settlementAdded);
    }

    @Test
    public void whenAddRoadNextToRoad_ThenReturnTrue() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Direction direction = Direction.ONE;
        Direction adjacentDirection = Direction.TWO;
        Tile tile = Tile.builder()
                .addRoad(direction, Road.of(Color.WHITE))
                .build(Resource.LUMBER, 4);
        Road adjacentRoad = Road.of(Color.WHITE);

        Board uut = Board.builder()
                .addTile(coordinate, tile)
                .build();

        boolean roadAdded = uut.addRoad(Position.of(coordinate, adjacentDirection), adjacentRoad);

        Assert.assertTrue(roadAdded);
    }

    @Test
    public void testWhenNoRoads_ThenLongestRoadReturnZero() {
        Board uut = Board.builder().build();
        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(0, longestRoad);
    }

    @Test
    public void testWhenOneRoad_ThenLongestRoadReturnOne() {
        Road road = Road.of(Color.RED);
        Tile tile = Tile.builder()
                .addRoad(Direction.ONE, road)
                .build(Resource.BRICK, 4);
        Board uut = Board.builder()
                .addTile(Coordinate.of(0, 0), tile)
                .build();

        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(1, longestRoad);
    }

    @Test
    public void testWhenRoadIsCircleAroundOneTile_ThenLongestRoadReturnSix() {
       Tile.Builder tileBuilder = Tile.builder();
        for (Direction direction : Direction.values()) {
            Road road = Road.of(Color.RED);
            tileBuilder.addRoad(direction, road);
        }
        Tile tile = tileBuilder.build(Resource.BRICK, 2);
        Board uut = Board.builder()
                .addTile(Coordinate.of(0, 0), tile)
                .build();

        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(6, longestRoad);
    }

    @Test
    public void testWhenRoadIsCircleWithOneWrongColor_ThenLongestRoadReturnFive() {
        Tile.Builder tileBuilder = Tile.builder();
        for (Direction direction : Direction.values()) {
            if (direction == Direction.ONE) {
                Road road = Road.of(Color.BLUE);
                tileBuilder.addRoad(direction, road);
                continue;
            }
            Road road = Road.of(Color.RED);
            tileBuilder.addRoad(direction, road);
        }
        Tile tile = tileBuilder.build(Resource.BRICK, 2);
        Board uut = Board.builder()
                .addTile(Coordinate.of(0, 0), tile)
                .build();

        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(5, longestRoad);
    }

    @Test
    public void testWhenRoadsAreOneSpaceApartOnTile_ThenLongestRoadReturnOne() {
        Tile tile = Tile.builder()
                .addRoad(Direction.ONE, Road.of(Color.RED))
                .addRoad(Direction.THREE, Road.of(Color.RED))
                .addRoad(Direction.FIVE, Road.of(Color.RED))
                .build(Resource.BRICK, 9);
        Board uut = Board.builder()
                .addTile(Coordinate.of(0, 1), tile)
                .build();

        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(1, longestRoad);
    }

    @Test
    public void testWhenRoadSpansAcrossMultipleTiles_ThenCorrectLengthReturned() {
        Tile firstTile = Tile.builder()
                .addRoad(Direction.ONE, Road.of(Color.RED))
                .build(Resource.BRICK, 6);
        Tile secondTile = Tile.builder()
                .addRoad(Direction.SIX, Road.of(Color.RED))
                .build(Resource.LUMBER, 2);
        Tile thirdTile = Tile.builder()
                .addRoad(Direction.FIVE, Road.of(Color.RED))
                .build(Resource.ORE, 4);
        Board uut = Board.builder()
                .addTile(Coordinate.of(0, 0), firstTile)
                .addTile(Coordinate.of(1, 0), secondTile)
                .addTile(Coordinate.of(2, -1), thirdTile)
                .build();

        int longestRoad = uut.getLongestRoad(Player.create(Color.RED));

        Assert.assertEquals(3, longestRoad);
    }
}
