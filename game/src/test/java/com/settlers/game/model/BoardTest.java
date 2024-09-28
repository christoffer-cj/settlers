package com.settlers.game.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class BoardTest {

    @Test
    public void whenNoTiles_ThenGetBuildingReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;

        Optional<Building> building = uut.getBuilding(coordinate, position);

        Assert.assertTrue(building.isEmpty());
    }

    @Test
    public void whenTileWithBuilding_ThenGetBuildingReturnBuilding() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(position, Building.builder().build(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(coordinate, position);

        Assert.assertTrue(building.isPresent());
    }

    @Test
    public void whenTileWithBuildingOnAnotherTile_ThenGetBuildingReturnBuilding() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Position position = Position.ONE;
        Position adjacentPosition = Position.FIVE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(position, Building.builder().build(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);

        Tile tileAdjacentToBuilding = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithBuilding)
                .addTile(adjacentCoordinate, tileAdjacentToBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(adjacentCoordinate, adjacentPosition);

        Assert.assertTrue(building.isPresent());
    }

    @Test
    public void whenTileWithBuildingButAskForTileWithNoBuilding_ThenGetBuildingReturnEmpty() {
        Coordinate coordinateWithBuilding = Coordinate.of(0, 0);
        Coordinate coordinateWithoutBuilding = Coordinate.of(420, 69);
        Position positionWithBuilding = Position.ONE;
        Position positionWithoutBuilding = Position.THREE;
        Tile tileWithBuilding = Tile.builder()
                .addBuilding(positionWithBuilding, Building.builder().build(Color.RED, Building.Type.CITY))
                .build(Resource.ORE, 8);
        Tile tileWithoutBuilding = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithBuilding, tileWithBuilding)
                .addTile(coordinateWithoutBuilding, tileWithoutBuilding)
                .build();

        Optional<Building> building = uut.getBuilding(coordinateWithoutBuilding, positionWithoutBuilding);

        Assert.assertTrue(building.isEmpty());
    }

    @Test
    public void whenNoTiles_ThenGetHarborReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;

        Optional<Harbor> harbor = uut.getHarbor(coordinate, position);

        Assert.assertTrue(harbor.isEmpty());
    }

    @Test
    public void whenTileWithHarbor_ThenGetHarborReturnHarbor() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(position, Harbor.LUMBER)
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(coordinate, position);

        Assert.assertTrue(harbor.isPresent());
    }

    @Test
    public void whenTileWithHarborOnAnotherTile_ThenGetHarborReturnHarbor() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Position position = Position.ONE;
        Position adjacentPosition = Position.FIVE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(position, Harbor.LUMBER)
                .build(Resource.ORE, 8);

        Tile tileAdjacentToHarbor = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithHarbor)
                .addTile(adjacentCoordinate, tileAdjacentToHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(adjacentCoordinate, adjacentPosition);

        Assert.assertTrue(harbor.isPresent());
    }

    @Test
    public void whenTileWithHarborButAskForTileWithNoHarbor_ThenGetHarborReturnEmpty() {
        Coordinate coordinateWithHarbor = Coordinate.of(0, 0);
        Coordinate coordinateWithoutHarbor = Coordinate.of(420, 69);
        Position positionWithHarbor = Position.ONE;
        Position positionWithoutHarbor = Position.THREE;
        Tile tileWithHarbor = Tile.builder()
                .addHarbor(positionWithHarbor, Harbor.ORE)
                .build(Resource.ORE, 8);
        Tile tileWithoutHarbor = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithHarbor, tileWithHarbor)
                .addTile(coordinateWithoutHarbor, tileWithoutHarbor)
                .build();

        Optional<Harbor> harbor = uut.getHarbor(coordinateWithoutHarbor, positionWithoutHarbor);

        Assert.assertTrue(harbor.isEmpty());
    }

    @Test
    public void whenNoTiles_ThenGetRoadReturnEmpty() {
        Board uut = Board.builder().build();
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;

        Optional<Road> road = uut.getRoad(coordinate, position);

        Assert.assertTrue(road.isEmpty());
    }

    @Test
    public void whenTileWithRoad_ThenGetRoadReturnRoad() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Position position = Position.ONE;
        Tile tileWithRoad = Tile.builder()
                .addRoad(position, Road.builder().build(Color.RED))
                .build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithRoad)
                .build();

        Optional<Road> road = uut.getRoad(coordinate, position);

        Assert.assertTrue(road.isPresent());
    }

    @Test
    public void whenTileWithRoadOnAnotherTile_ThenGetRoadReturnRoad() {
        Coordinate coordinate = Coordinate.of(0, 0);
        Coordinate adjacentCoordinate = Coordinate.of(1, -1);
        Position position = Position.ONE;
        Position adjacentPosition = Position.FOUR;
        Tile tileWithRoad = Tile.builder()
                .addRoad(position, Road.builder().build(Color.RED))
                .build(Resource.ORE, 8);

        Tile tileAdjacentToRoad = Tile.builder().build(Resource.ORE, 8);

        Board uut = Board.builder()
                .addTile(coordinate, tileWithRoad)
                .addTile(adjacentCoordinate, tileAdjacentToRoad)
                .build();

        Optional<Road> road = uut.getRoad(adjacentCoordinate, adjacentPosition);

        Assert.assertTrue(road.isPresent());
    }

    @Test
    public void whenTileWithRoadButAskForTileWithNoRoad_ThenGetRoadReturnEmpty() {
        Coordinate coordinateWithRoad = Coordinate.of(0, 0);
        Coordinate coordinateWithoutRoad = Coordinate.of(420, 69);
        Position positionWithRoad = Position.ONE;
        Position positionWithoutRoad = Position.THREE;
        Tile tileWithRoad = Tile.builder()
                .addRoad(positionWithRoad, Road.builder().build(Color.RED))
                .build(Resource.ORE, 8);
        Tile tileWithoutRoad = Tile.builder().build(Resource.LUMBER, 4);

        Board uut = Board.builder()
                .addTile(coordinateWithRoad, tileWithRoad)
                .addTile(coordinateWithoutRoad, tileWithoutRoad)
                .build();

        Optional<Road> road = uut.getRoad(coordinateWithoutRoad, positionWithoutRoad);

        Assert.assertTrue(road.isEmpty());
    }
}
