package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;

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
    private PrintWriter printWriter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientCommunicator(ClientService serverService) {
        this.serverService = serverService;
        printWriter = serverService.getPrintWriter();
    }

    public void sendGameInvitation() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(Request.gameInvitation());
        //String json = objectMapper.writeValueAsString(new Request(RequestType.GAME_INVITATION.name(), null));
        printWriter.println(json);
    }

    public void startCommunication() throws JsonProcessingException {
        sendGameInvitation();
    }
}
