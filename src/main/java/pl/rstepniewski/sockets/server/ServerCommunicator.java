package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private final ServerService serverService;

    public ServerCommunicator(ServerService serverService) {
        this.serverService  = serverService;
        bufferedReader      = serverService.getBufferedReader();
        printWriter         = serverService.getPrintWriter();
    }

    public void handleGame() throws IOException {

        String responseJson = bufferedReader.readLine();

        Response response = objectMapper.readValue(responseJson, Response.class);
        logger.info("Request received from client. Request type: " + response.type());
        if(response.type().equals(ResponseType.GAME_INVITATION.name())){
            logger.info("Server accepted game invitation");
            printWriter.println(new Response(ResponseType.GAME_INVITATION.name(), 0, null, null ));
        }else {
            logger.info("Server decline game invitation");
            printWriter.println(new Response(ResponseType.GAME_INVITATION.name(), 1, "Server is playing the other game.", null ));
        }


    }
}
