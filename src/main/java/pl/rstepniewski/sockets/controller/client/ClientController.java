package pl.rstepniewski.sockets.controller.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.communication.ClientCommunicatorImpl;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.*;
import pl.rstepniewski.sockets.game.board.BoardCellStatus;
import pl.rstepniewski.sockets.game.board.GameBoard;
import pl.rstepniewski.sockets.game.board.GameBoardUserController;
import pl.rstepniewski.sockets.jsonCommunication.MessageType;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;
import java.io.IOException;
import java.util.Objects;

import static pl.rstepniewski.sockets.jsonCommunication.MessageType.GAME_INVITATION;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientController extends ClientCommunicatorImpl {
    private final GameBoardUserController gameBoardUserController;
    private Response response;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(ClientController.class);
    private boolean isOpponendDefeated = false;

    public ClientController() {
        super(new ClientService());
        this.gameBoardUserController = new GameBoardUserController(new GameBoard());
    }

    public void playGame() throws IOException {
        logger.info("Starting Battleship game");
        sendGameInvitation();
        handleServerInvitationResponse();
        gameBoardUserController.initializeBoard();

        while (gameBoardUserController.isFleetAlive()) {
            Point shot = shoot();
            response = getResponse();
            markShotResult(shot, response);
            sendRequest(Request.shotRequest());
            Response shotResponse = getResponse();

            if(MessageType.getMessageTypeFromString(shotResponse.getType()) == MessageType.SHOT){
                if(Objects.isNull(shotResponse.getBody())){
                    UserInterface.printProperty("win");
                    return;
                }
            }
            handleShotResponse(shotResponse);
        }
        UserInterface.printProperty("loose");

    }

    public void sendGameInvitation() throws JsonProcessingException {
        sendRequest(Request.gameInvitation());
    }

    private void handleServerInvitationResponse() throws IOException {
        Response response = getResponse();
        if ( response.getType().equals(GAME_INVITATION.name()) && response.getStatus() != 0) {
            UserInterface.printText(response.getMessage());
        }
    }

    private void handleShotResponse(Response response) throws IOException {
        Request requestResult= null;
        ShotDto point = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody()), ShotDto.class);

        Point receivedShot = new Point(point.getRow(), point.getColumn());
        boolean isShotAccurate = gameBoardUserController.isShotHit(receivedShot);
        gameBoardUserController.reportReceivedShot(receivedShot);
        if (isShotAccurate) {
            boolean isShipSinking = gameBoardUserController.markHitOnShip(receivedShot);
            gameBoardUserController.markHitOnFleetBoard(receivedShot, BoardCellStatus.HIT);
            if (isShipSinking) {
                requestResult = Request.shotResultSinking();

            } else {
                requestResult = Request.shotResultHit();
            }
        } else {
            gameBoardUserController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
            requestResult = Request.shotResultMiss();
        }
        sendRequest(requestResult);
    }

    private void markShotResult(Point shot, Response response) {
        if (response.getStatus() == 2) {
            UserInterface.printText(response.getMessage().toString());
        }
        switch (response.getBody().toString()) {
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
        sendRequest(Request.shot( new ShotDto(shot.getX(), shot.getY()) ));

        return shot;
    }
}
