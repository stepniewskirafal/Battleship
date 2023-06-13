package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.game.ShipPosition;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientCommunicator {
    private final ClientService serverService;
    private PrintWriter printWriter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientCommunicator(ClientService serverService) {
        this.serverService = serverService;
        printWriter = serverService.getPrintWriter();
    }

    public void sendGameInvitation() throws JsonProcessingException {
        Request request = Request.shipsArrangement(new ShipPosition("qweq",
                new String[]{"3", "3"},
                new String[]{"2", "2", "2"},
                new String[]{"1", "1", "1", "1"}));
        String json = objectMapper.writeValueAsString(request);
        printWriter.println(json);
    }

    public void playGame() throws JsonProcessingException {
        sendGameInvitation();
    }
}
