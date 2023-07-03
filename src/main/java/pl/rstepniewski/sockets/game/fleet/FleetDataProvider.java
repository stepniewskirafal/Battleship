package pl.rstepniewski.sockets.game.fleet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FleetDataProvider {
    String FILE_PATH = "/fleet.ai";

    static List<Map<String, Object>> getFleetData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = Fleet.class.getResourceAsStream(FILE_PATH)) {
            if (inputStream != null) {
                return objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
            } else {
                System.err.println("Brakuje pliku filePath");
            }
        }
        return null;
    }
}