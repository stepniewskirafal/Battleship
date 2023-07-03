package pl.rstepniewski.sockets.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by rafal on 02.07.2023
 *
 * @author : rafal
 * @date : 02.07.2023
 * @project : Battleship
 */
public class FleetLoader {
    private static final String FILE_PATH = "/fleet.ai";

    public static Fleet loadRandomFleet() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = Fleet.class.getResourceAsStream(FILE_PATH)) {
            if (inputStream != null) {
                List<Map<String, Object>> jsonDataList = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
                int randomIndex = new Random().nextInt(jsonDataList.size());
                Map<String, Object> jsonData = jsonDataList.get(randomIndex);

                List<Ship> fleet = parseFleetData(jsonData);

                return new Fleet(fleet);
            } else {
                System.err.println("Brakuje pliku filePath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Ship> parseFleetData(Map<String, Object> jsonData) {
        List<Ship> fleet = new ArrayList<>();

        for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
            String shipType = entry.getKey();
            Object coordinates = entry.getValue();

            if (coordinates instanceof List) {
                List<List<String>> coordinateLists = (List<List<String>>) coordinates;
                for (List<String> coordinateList : coordinateLists) {
                    fleet.add(createShip(shipType, coordinateList));
                }
            } else if (coordinates instanceof String) {
                fleet.add(createShip(shipType, (String) coordinates));
            }
        }
        return fleet;
    }

    private static Ship createShip(String shipType, List<String> coordinates) {
        List<Point> shipCoordinates = new ArrayList<>();
        for (String coordinate : coordinates) {
            shipCoordinates.add(new Point(coordinate));
        }
        Ship ship = new Ship(shipCoordinates, convertToNumber(shipType));
        System.out.println(ship);
        return ship;
    }

    private static Ship createShip(String shipType, String coordinate) {
        List<Point> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new Point(coordinate));

        return new Ship(shipCoordinates, convertToNumber(shipType));
    }

    private static int convertToNumber(String shipType) {
        switch (shipType){
            case "4-mast": return 4;
            case "3-mast": return 3;
            case "2-mast": return 2;
            case "1-mast": return 1;
            default: return 0;
        }
    }


}