package pl.rstepniewski.sockets.game.fleet;

import pl.rstepniewski.sockets.game.Ship;

import java.io.IOException;
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

    public FleetLoader() {
    }

    public Fleet loadRandomFleet() {
        try {
            List<Map<String, Object>> jsonDataList = FleetDataProvider.getFleetData();
            int randomIndex = new Random().nextInt(jsonDataList.size());
            Map<String, Object> jsonData = jsonDataList.get(randomIndex);

            List<Ship> fleet = FleetParser.parseFleetData(jsonData);

            return new Fleet(fleet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
