package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.json.Request;
import pl.rstepniewski.sockets.json.RequestType;

import java.io.PrintWriter;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientCommunicator {
    private final ClientService serverService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientCommunicator(ClientService serverService) {
        this.serverService = serverService;
    }

    public void startComunication() throws JsonProcessingException {
        PrintWriter printWriter = serverService.getPrintWriter();

        Request request = new Request(RequestType.GAME_INVITATION.name(), null);
        String json = objectMapper.writeValueAsString(request);
        printWriter.println(json);
    }
}
