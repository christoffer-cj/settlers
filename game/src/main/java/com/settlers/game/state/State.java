package com.settlers.game.state;

import com.settlers.game.Building;
import com.settlers.game.Position;
import com.settlers.game.Road;

public interface State { // todo use dependency injection to make state classes more testable
    boolean rollDice();

    boolean addBuilding(Position position, Building building);

    boolean addRoad(Position position, Road road);
}
