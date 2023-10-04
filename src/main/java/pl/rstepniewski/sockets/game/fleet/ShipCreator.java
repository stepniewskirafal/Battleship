package pl.rstepniewski.sockets.game.fleet;

import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.Ship;

import java.util.ArrayList;
import java.util.List;

public interface ShipCreator {
    static Ship createShip(String shipType, List<String> coordinates) {
        List<Point> shipCoordinates = new ArrayList<>();
        for (String coordinate : coordinates) {
            shipCoordinates.add(new Point(coordinate));
        }
        return new Ship(shipCoordinates, ShipTypeConverter.convertToNumber(shipType));
    }

    static Ship createShip(String shipType, String coordinate) {
        List<Point> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new Point(coordinate));
        return new Ship(shipCoordinates, ShipTypeConverter.convertToNumber(shipType));
    }
}