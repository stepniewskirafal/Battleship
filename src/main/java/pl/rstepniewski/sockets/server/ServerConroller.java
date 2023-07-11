package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.communication.ServerCommunicatorImpl;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.*;
import pl.rstepniewski.sockets.game.board.BoardCellStatus;
import pl.rstepniewski.sockets.game.board.GameBoard;
import pl.rstepniewski.sockets.game.board.GameBoardAIController;
import pl.rstepniewski.sockets.game.fleet.FleetLoader;
import pl.rstepniewski.sockets.jsonCommunication.MessageType;
import pl.rstepniewski.sockets.jsonCommunication.message.Message;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

import java.io.IOException;

import static pl.rstepniewski.sockets.jsonCommunication.ResponseType.GAME_INVITATION;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ServerConroller extends ServerCommunicatorImpl {
    private static final Logger logger = LogManager.getLogger(ServerConroller.class);
    private final GameBoardAIController gameBoardAIController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Response response;
    private boolean serverGameBussy = false;

    public ServerConroller() {
        super(new ServerService());
        this.gameBoardAIController  = new GameBoardAIController(new GameBoard(), new FleetLoader());
    }

    public void handleGame() throws IOException {

        while (true) {
            Message clientMessage = getClientMessage();
            MessageType messageTypeFromString = MessageType.getMessageTypeFromString(clientMessage.getType());
            switch (messageTypeFromString) {
                case GAME_INVITATION:
                    handleGameInvitation(messageTypeFromString.name());
                    break;
                case RESULT:
                    break;
                case SHOT:
                    break;
                case SHOT_REQUEST:
                    break;
                case UNKNOWN:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + clientMessage.getType());
            }
        }

/*        while (gameBoardAIController.isFleetAlive()) {
            Request shotRequest = getRequest();
            handleShotRequest(shotRequest);

            Point shot = shoot();
            response = getResponse();
            markShootResut(shot, response);
        }*/
    }

    private void handleGameInvitation(String requestType) throws IOException {
        logger.info("Request received from client. Request type: " + requestType);
        if(! serverGameBussy ) {
            serverGameBussy=!serverGameBussy;
            response = Response.gameInvitationNegative();
            sendResponse(response);
            gameBoardAIController.initialiseBord();
        }else {
            response = Response.gameInvitationNegative();
            sendResponse(response);
        }
    }

    private void markShootResut(Point shot, Response response) {
        if (response.getStatus() == 2) {
            UserInterface.printText(response.getMessage().toString());
        }
        switch (response.getBody().toString()) {
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

    private Point shoot() throws JsonProcessingException {
        Point shot = gameBoardAIController.getNewRandomShot();
        logger.info("The coordinates of the shot generated by server are x:" + shot.getX() + " y:" + shot.getY());

        sendRequest(Request.shot(new ShotDto(shot.getX(), shot.getY())));
        return shot;
    }

    private void handleShotRequest(Request request) throws IOException {
        if (request.getType().equals(RequestType.SHOT.name())) {
            ShotDto point = objectMapper.readValue(objectMapper.writeValueAsString(request.getBody()), ShotDto.class);
            Point receivedShot = new Point(point.getRow(), point.getColumn());
            boolean isShotAccurate = gameBoardAIController.isShotHit(receivedShot);
            gameBoardAIController.reportReceivedShot(receivedShot);

            if (isShotAccurate) {
                boolean isShipSinking = gameBoardAIController.markHitOnShipBoard(receivedShot);
                gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.HIT);
                if (isShipSinking) {
                    sendResponse(Response.shotResultSinking());
                } else {
                    sendResponse(Response.shotResultHit());
                }
            } else {
                gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
                sendResponse(Response.shotResultMiss());
            }

        }
    }
}
