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
import java.io.IOException;

import static pl.rstepniewski.sockets.jsonCommunication.ResponseType.GAME_INVITATION;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientController {
    private final ClientCommunicatorImpl communicator;
    private final GameBoardUserController gameBoardUserController;
    private Response response;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(ClientController.class);

    public ClientController(ClientCommunicatorImpl communicator) {
        this.gameBoardUserController = new GameBoardUserController(new GameBoard());
        this.communicator            = communicator;
    }

    public void playGame() throws IOException {
        logger.info("Starting Battleship game");
        sendGameInvitation();
        handleServerInvitationResponse();
        gameBoardUserController.initializeBoard();

        while (gameBoardUserController.isFleetAlive()) {
            Point shot = shoot();
            response = communicator.getShotResult();
            markShotResult(shot, response);

            Request shotRequest = communicator.getShotRequest();
            handleShotRequest(shotRequest);
        }

        UserInterface.printProperty("win");
    }

    public void sendGameInvitation() throws JsonProcessingException {
        Request request = Request.gameInvitation();
        communicator.sendMessage(objectMapper.writeValueAsString(request));
    }

    private void handleServerInvitationResponse() throws IOException {
        Response response = communicator.getInvitationResponse();
        if ( response.type().equals(GAME_INVITATION.name()) && response.status() != 0) {
            UserInterface.printText(response.message());
        }
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
                    communicator.sendMessage(gameShotStatusResponse);
                } else {
                    gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultHit());
                    communicator.sendMessage(gameShotStatusResponse);
                }
            } else {
                gameBoardUserController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
                gameShotStatusResponse = objectMapper.writeValueAsString(Response.shotResultMiss());
                communicator.sendMessage(gameShotStatusResponse);
            }
        }
    }

    private void markShotResult(Point shot, Response response) {
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

    private Point shoot() throws JsonProcessingException {
        Point shot = ShotInterface.getNewUserShot();
        Request request = Request.shot( new ShotDto(shot.getX(), shot.getY()) );
        String json = objectMapper.writeValueAsString(request);
        communicator.sendMessage(json);
        return shot;
    }
}
