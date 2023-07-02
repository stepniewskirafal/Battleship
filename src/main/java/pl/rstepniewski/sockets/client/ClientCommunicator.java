package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ClientCommunicator.class);

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
            response = getShotResut();
            markShootResut(shot, response);

            Request shotRequest = getShotRequest();
            handleShotRequest(shotRequest);
        }

        UserInterface.printProperty("win");
    }

    private Request getShotRequest() throws IOException {
        jsonString = bufferedReader.readLine();
        request = objectMapper.readValue(jsonString, Request.class);
        logger.info("The coordinates of the shot sent by user: "+ request.body().toString());
        return request;
    }

    private void handleShotRequest(Request request) throws IOException {
        if (request.type().equals(RequestType.SHOT.name())) {
            ShotDto point = objectMapper.readValue(objectMapper.writeValueAsString(request.body()), ShotDto.class);
            Point receivedShot = new Point(point.getX(), point.getY());
            boolean isShotAccurate = gameBoardUserController.isShotHit(receivedShot);
            gameBoardUserController.reportReceivedShot(receivedShot);
            String gameShotStatusResponse;
            if (isShotAccurate) {
                boolean isShipSinking = gameBoardUserController.markHitOnShip(receivedShot);
                gameBoardUserController.markHitOnFleetBoard(receivedShot, BoardCellStatus.HIT);
                if (isShipSinking) {
                    gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultSinking());
                    printWriter.println(gameShotStatusResponse);
                } else {
                    gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultHit());
                    printWriter.println(gameShotStatusResponse);
                }
            } else {
                gameBoardUserController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
                gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultMiss());
                printWriter.println(gameShotStatusResponse);
            }
        }
    }

    private void markShootResut(Point shot, Response response) {
        if (response.status() == 2) {
            UserInterface.printText(response.message().toString());
        }
        switch (response.body().toString()) {
            case "HIT":
                gameBoardUserController.markHitOnShotBoard(shot);
                break;
            case "MISS":
                gameBoardUserController.markMissOnShotBoard(shot);
                break;
            case "SINKING":
                gameBoardUserController.markSinkingOnShotBoard(shot);
        }
    }

    private Response getShotResut() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }

    private Point shoot() throws JsonProcessingException {
        Point shot = ShotInterface.getNewUserShot();
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
