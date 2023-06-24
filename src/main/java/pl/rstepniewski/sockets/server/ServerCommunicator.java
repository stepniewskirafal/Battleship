package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.game.GameBoard;
import pl.rstepniewski.sockets.game.GameBoardAIController;
import pl.rstepniewski.sockets.game.GameBoardUserController;
import pl.rstepniewski.sockets.jsonCommunication.Request;
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

        while (true) {
            jsonString = bufferedReader.readLine();
            request = objectMapper.readValue(jsonString, Request.class);
            System.out.println(request.body().toString());

            String gameShotStatusResponse = objectMapper.writeValueAsString(new Response(ResponseType.SHOT.name(), 0, null, "HIT"));
            printWriter.println(gameShotStatusResponse);
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
