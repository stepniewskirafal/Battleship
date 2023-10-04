package pl.rstepniewski.sockets.game.fleet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.board.GameBoardUserController;

import java.io.IOException;
import java.io.InputStream;
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
    static final String FILE_PATH = "/fleet.ai";
    private static final Logger LOGGER = LogManager.getLogger(GameBoardUserController.class);

    public Fleet loadRandomFleet() {
        try {
            List<Map<String, Object>> jsonDataList = getFleetData();
            int randomIndex = new Random().nextInt(jsonDataList.size());
            Map<String, Object> jsonData = jsonDataList.get(randomIndex);

            List<Ship> fleet = FleetParser.parseFleetData(jsonData);

            return new Fleet(fleet);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    private List<Map<String, Object>> getFleetData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> maps;
        try (InputStream inputStream = Fleet.class.getResourceAsStream(FILE_PATH)) {
            maps = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
            });
        }
        return maps;
    }
}
