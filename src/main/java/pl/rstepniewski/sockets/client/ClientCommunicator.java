package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.*;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;
import pl.rstepniewski.sockets.jsonCommunication.Response;
import pl.rstepniewski.sockets.jsonCommunication.ResponseType;

import java.io.BufferedReader;
import java.io.IOException;
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
    private BufferedReader bufferedReader;
    GameBoardUserController gameBoardUserController = new GameBoardUserController(new GameBoard());
    private String jsonString;
    private Request request;
    private Response response;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientCommunicator(ClientService serverService) {
        this.serverService = serverService;
        printWriter = serverService.getPrintWriter();
        bufferedReader = serverService.getBufferedReader();
    }

    public void playGame() throws IOException {
        sendGameInvitation();

        handleServerInvitationResponce();

        gameBoardUserController.initialiseBord();

        while (gameBoardUserController.isFleetAlive()) {
            Point shot = shoot();
            response = getShotResutFromServer();
            markShootResut(shot, response);
        }

    }

    private void markShootResut(Point shot, Response response) {
        if (response.status() == 2) {
            UserInterface.printText(response.message().toString());
        }
        switch (response.body().toString()) {
            case "HIT":
                gameBoardUserController.markHitOnShortBoard(shot);
            case "MISS":
                gameBoardUserController.markMissOnShortBoard(shot);
            case "SINKING":
                gameBoardUserController.markSinkingOnShortBoard(shot);
        }
    }

    private Response getShotResutFromServer() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }

    private Point shoot() throws JsonProcessingException {
        Point shot = ShotInterface.getNewShot();
        Request request = Request.shot( new ShotDto(shot.getX(), shot.getY()) );
        String json = objectMapper.writeValueAsString(request);
        printWriter.println(json);
        return shot;
    }

    private void handleServerInvitationResponce() throws IOException {
        Response response = getInvitationResponse();
        if ( response.type().equals(ResponseType.GAME_INVITATION.name()) && response.status() != 0) {
            UserInterface.printText(response.message());
        }
    }

    public void sendGameInvitation() throws JsonProcessingException {
        Request request = new Request(RequestType.GAME_INVITATION.name(), null);
        String json = objectMapper.writeValueAsString(request);
        printWriter.println(json);
    }

    private Response getInvitationResponse() throws IOException {
        String responseJson = bufferedReader.readLine();
        Response response = objectMapper.readValue(responseJson, Response.class);
        return response;
    }
}
