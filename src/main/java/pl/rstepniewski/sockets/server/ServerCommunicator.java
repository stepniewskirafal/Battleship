package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.json.Response;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ServerCommunicator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ServerService serverService;

    public ServerCommunicator(ServerService serverService) {
        this.serverService = serverService;
    }

    public void startComunication() throws IOException {
        BufferedReader bufferedReader = serverService.getBufferedReader();

        String responseJson = bufferedReader.readLine();

        Response response = objectMapper.readValue(responseJson, Response.class);

        System.out.println(response.getType());
    }
}
