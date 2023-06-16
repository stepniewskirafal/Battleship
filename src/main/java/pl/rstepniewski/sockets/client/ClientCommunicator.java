package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.game.*;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;
import pl.rstepniewski.sockets.jsonCommunication.Response;
import pl.rstepniewski.sockets.jsonCommunication.ResponseType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static pl.rstepniewski.sockets.game.UserInterface.printText;

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
    private BufferedReader bufferedReader;
    private UserInterface userInterface;
    private Board board;
    GameController gameController = new GameController(userInterface, board);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientCommunicator(ClientService serverService) {
        this.serverService = serverService;
        printWriter = serverService.getPrintWriter();
        bufferedReader = serverService.getBufferedReader();
    }

    public void sendGameInvitation() throws JsonProcessingException {
        Request request = new Request(RequestType.GAME_INVITATION.name(), null);
        String json = objectMapper.writeValueAsString(request);
        printWriter.println(json);
    }

    public void playGame() throws IOException {
        sendGameInvitation();
        String responseJson = bufferedReader.readLine();

        Response response = objectMapper.readValue(responseJson, Response.class);

        if ( response.type().equals(ResponseType.GAME_INVITATION.name()) && response.status() != 0) {
            printText(response.message());
            return;
        }

        gameController.runGame();


        Point shot = new Point("A5");
        Request request = new Request(RequestType.SHOT_REQUEST.name(), shot);
        String json = objectMapper.writeValueAsString(request);

    }
}
