package pl.rstepniewski.sockets.game.fleet;

import pl.rstepniewski.sockets.game.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FleetParser {
    static List<Ship> parseFleetData(Map<String, Object> jsonData) {
        List<Ship> fleet = new ArrayList<>();

        for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
            String shipType = entry.getKey();
            Object coordinates = entry.getValue();

            if (coordinates instanceof List) {
                List<List<String>> coordinateLists = (List<List<String>>) coordinates;
                for (List<String> coordinateList : coordinateLists) {
                    fleet.add(ShipCreator.createShip(shipType, coordinateList));
                }
            } else if (coordinates instanceof String) {
                fleet.add(ShipCreator.createShip(shipType, (String) coordinates));
            }
        }
        return fleet;
    }
}