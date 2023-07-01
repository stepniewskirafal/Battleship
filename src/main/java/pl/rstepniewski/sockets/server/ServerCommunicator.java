package pl.rstepniewski.sockets.server;

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
public class ServerCommunicator {
    private static final Logger logger = LogManager.getLogger(ServerCommunicator.class);
    GameBoardAIController gameBoardAIController = new GameBoardAIController(new GameBoard());
    private final ObjectMapper objectMapper = new ObjectMapper();

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private final ServerService serverService;
    private String jsonString;
    private Request request;
    private Response response;

    public ServerCommunicator(ServerService serverService) {
        this.serverService  = serverService;
        bufferedReader      = serverService.getBufferedReader();
        printWriter         = serverService.getPrintWriter();
    }

    public void handleGame() throws IOException {

        handleGameInvitation();

        gameBoardAIController.initialiseBord();

        while (gameBoardAIController.isFleetAlive()) {
            Request shotRequest = getShotRequest();
            handleShotRequest(shotRequest);

            Point shot = shoot();
            response = getShotResut();
            markShootResut(shot, response);
        }
    }

    private void markShootResut(Point shot, Response response) {
        if (response.status() == 2) {
            UserInterface.printText(response.message().toString());
        }
        switch (response.body().toString()) {
            case "HIT":
                gameBoardAIController.markHitOnShotBoard(shot);
                break;
            case "MISS":
                gameBoardAIController.markMissOnShotBoard(shot);
                break;
            case "SINKING":
                gameBoardAIController.markSinkingOnShotBoard(shot);
        }
    }

    private Response getShotResut() throws IOException {
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
            boolean isShotAccurate = gameBoardAIController.isShotHit(receivedShot);
            String gameShotStatusResponse;
            if (isShotAccurate) {
                boolean isShipSinking = gameBoardAIController.markHitOnShipBoard(receivedShot);
                if (isShipSinking) {
                    gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultSinking());
                    printWriter.println(gameShotStatusResponse);
                } else {
                    gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultHit());
                    printWriter.println(gameShotStatusResponse);
                }
            } else {
                gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultMiss());
                printWriter.println(gameShotStatusResponse);
            }
            System.out.println(gameShotStatusResponse);
        }
    }

    private void handleGameInvitation() throws IOException {
        jsonString = bufferedReader.readLine();
        request = objectMapper.readValue(jsonString, Request.class);
        logger.info("Request received from client. Request type: " + request.type());
        if(!request.type().equals(ResponseType.GAME_INVITATION.name())) {
            logger.info("Server decline game invitation");
            String gameInvitationNegativeResponse = objectMapper.writeValueAsString(new Response(ResponseType.GAME_INVITATION.name(), 1, "Server is playing the other game.", null));
            printWriter.println(gameInvitationNegativeResponse);
        }
        logger.info("Server accepted game invitation");
        String gameInvitationPositiveResponse = objectMapper.writeValueAsString(new Response(ResponseType.GAME_INVITATION.name(), 0, null, null));
        printWriter.println(gameInvitationPositiveResponse);
    }
}
