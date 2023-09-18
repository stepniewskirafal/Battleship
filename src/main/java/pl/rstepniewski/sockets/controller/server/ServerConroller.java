package pl.rstepniewski.sockets.controller.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.communication.ServerCommunicatorImpl;
import pl.rstepniewski.sockets.dto.ShipDto;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.*;
import pl.rstepniewski.sockets.game.board.BoardCellStatus;
import pl.rstepniewski.sockets.game.board.GameBoard;
import pl.rstepniewski.sockets.game.board.GameBoardAIController;
import pl.rstepniewski.sockets.game.fleet.FleetLoader;
import pl.rstepniewski.sockets.jsonCommunication.MessageType;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

import java.io.IOException;
import java.util.List;

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
    private boolean serverGameBussy = false;

    public ServerConroller() {
        super(new ServerService());
        this.gameBoardAIController  = new GameBoardAIController(new GameBoard(), new FleetLoader());
    }

    public void handleGame() throws IOException {
        Point point = null;

        while (true) {
            final String clientMessage = getClientMessage();

            MessageType messageTypeFromString = getMessageType(clientMessage);

            switch (messageTypeFromString) {
                case BOARD:
                    handleOpponentFleetSetting(clientMessage);
                    acceptOpponentFleetSetting();
                    break;
                case GAME_INVITATION:
                    handleGameInvitation();
                    break;
                case RESULT:
                    markShootResut(point, objectMapper.readValue(clientMessage, Response.class));
                    acceptShotResult();
                    break;
                case SHOT:
                     handleOpponentShoot( objectMapper.readValue(clientMessage, Request.class) );
                    break;
                case SHOT_REQUEST:
                    point = handleAShootRequest();
                    break;
                case UNKNOWN:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + messageTypeFromString);
            }
        }
    }

    private void handleOpponentFleetSetting(String clientMessage) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(clientMessage);
        final String clientMessageBody = String.valueOf(jsonNode.get("body"));
        List<ShipDto> OpponentFleetSetting = objectMapper.readValue(clientMessageBody, new TypeReference<List<ShipDto>>() {});

    }

    private void acceptOpponentFleetSetting() throws JsonProcessingException {
        sendResponse(Response.acceptOpponentFleetSetting());
    }

    private void acceptShotResult() throws JsonProcessingException {
        sendResponse(Response.acceptShotResult());
    }

    private MessageType getMessageType(String clientMessage) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(clientMessage);
        final String clientMessageType = jsonNode.get("type").asText();

        return MessageType.getMessageTypeFromString(clientMessageType);
    }

    private void handleGameInvitation() throws IOException {
        logger.info("Request received from client. Request type: GameInvitation");
        Response response;

        if(! serverGameBussy ) {
            serverGameBussy=!serverGameBussy;
            response = Response.gameInvitationPositive();
            sendResponse(response);
            gameBoardAIController.initialiseBord();
        }else {
            response = Response.gameInvitationNegative();
            sendResponse(response);
        }
    }

    private void markShootResut(Point shot, Response response) {
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

    private Point handleAShootRequest() throws JsonProcessingException {
        Point shot = null;
        Response responseShotRequestResult = null;
        shot = gameBoardAIController.getNewRandomShot();
        logger.info("The coordinates of the shot generated by server are x:" + shot.getX() + " y:" + shot.getY());

        responseShotRequestResult = Response.shot(new ShotDto(shot.getX(), shot.getY()));

        sendResponse(responseShotRequestResult);

        return shot;
    }

    private void handleOpponentShoot(Request request) throws IOException {
        Response responseShotResult = null;

        final ShotDto point = objectMapper.readValue(objectMapper.writeValueAsString(request.getBody()), ShotDto.class);
        final Point receivedShot = new Point(point.getRow(), point.getColumn());

        if(! gameBoardAIController.isShotInBoundaries(receivedShot) ){
            responseShotResult = Response.shotResultShotNotInBoundaries();
        }else {
            final boolean isShotAccurate = gameBoardAIController.isShotHit(receivedShot);
            gameBoardAIController.reportReceivedShot(receivedShot);

            if (isShotAccurate) {
                boolean isShipSinking = gameBoardAIController.markHitOnShipBoard(receivedShot);
                gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.HIT);
                if (isShipSinking) {
                    responseShotResult = Response.shotResultShipSinking();
                } else {
                    responseShotResult = Response.shotResultHit();
                }
            } else {
                gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
                responseShotResult = Response.shotResultMiss();
            }
        }

        sendResponse(responseShotResult);
    }
}
