package pl.rstepniewski.sockets.controller.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.communication.ClientCommunicatorImpl;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.GetPointInterface;
import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.UserInterface;
import pl.rstepniewski.sockets.game.board.BoardCellStatus;
import pl.rstepniewski.sockets.game.board.GameBoard;
import pl.rstepniewski.sockets.game.board.GameBoardAIController;
import pl.rstepniewski.sockets.game.board.GameBoardUserController;
import pl.rstepniewski.sockets.game.fleet.FleetLoader;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.rstepniewski.sockets.jsonCommunication.MessageType.GAME_INVITATION;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientAIController extends ClientCommunicatorImpl {
    private final GameBoardAIController gameBoardAIController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(ClientAIController.class);
    private int hitCounter;

    ArrayList<Point> shotsHistory = new ArrayList<>();

    public ClientAIController() {
        super(new ClientService());
        this.gameBoardAIController = new GameBoardAIController(new GameBoard(), new FleetLoader());
    }

    public void playGame() throws IOException {
        Response response;
        boolean isShotPositive;
        boolean amITheWinner = false;
        logger.info("Starting Battleship game");
        sendGameInvitation();
        handleServerInvitationResponse();
        gameBoardAIController.initialiseBord();

        while (gameBoardAIController.isFleetAlive() && (!amITheWinner) ) {
            do{
                Point shot = shootOpponentsFleet();
                response = getResponse();
                isShotPositive = markShotResult(shot, response);
            }while (!isShotPositive);

            amITheWinner = amITheWinner(response);
            if(!amITheWinner){
                sendRequest(Request.shotRequest());
                response = getResponse();
                handleShotResponse(response);
                response = getResponse();
            }
        }

        printGameResult(amITheWinner);
        sendRequest(Request.shipsArrangement(gameBoardAIController.getFleetAsShipDtoList()));
        response = getResponse();
    }

    public void endGame() throws IOException {
        stopCommunicator();
    }

    private static void printGameResult(boolean amITheWinner) {
        if(!amITheWinner) {
            UserInterface.printProperty("loose");
        }else {
            UserInterface.printProperty("win");
        }
    }

    private boolean amITheWinner(Response response) {
        final String responseBodyString = response.getBody().toString();
        if (responseBodyString.equals("HIT") || responseBodyString.equals("SINKING")) {
            hitCounter++;
        }
        return hitCounter >= 20;
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
        boolean isShotAccurate = gameBoardAIController.isShotHit(receivedShot);
        gameBoardAIController.reportReceivedShot(receivedShot);
        if (isShotAccurate) {
            boolean isShipSinking = gameBoardAIController.markHitOnShipBoard(receivedShot);
            gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.HIT);
            if (isShipSinking) {
                requestResult = Request.shotResultSinking();

            } else {
                requestResult = Request.shotResultHit();
            }
        } else {
            gameBoardAIController.markHitOnFleetBoard(receivedShot, BoardCellStatus.MISS);
            requestResult = Request.shotResultMiss();
        }
        sendRequest(requestResult);
    }

    private boolean markShotResult(Point shot, Response response) {
        boolean result = false;
        if (response.getStatus() == 2) {
            UserInterface.printText(response.getMessage());
            result = false;
        } else{
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
            result = true;
        }
        return result;
    }

    private Point shootOpponentsFleet() throws JsonProcessingException {
        UserInterface.printProperty("shoot.prompt");
        boolean isPointReady = false;
        Point shot = null;
        do{
            final Point finalShotRandom = GetPointInterface.getNewRandomPoint();

            boolean pointFound = shotsHistory.stream()
                    .anyMatch(point -> point.equals(finalShotRandom));

            if (!pointFound) {
                shotsHistory.add(finalShotRandom);
                shot = finalShotRandom;
                isPointReady = true;
            }
        }while (!isPointReady);

        sendRequest(Request.shot( new ShotDto(shot.getX(), shot.getY()) ));
        return shot;
    }

}
