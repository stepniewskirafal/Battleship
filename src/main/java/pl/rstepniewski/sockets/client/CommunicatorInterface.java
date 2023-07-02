package pl.rstepniewski.sockets.client;

import java.io.IOException;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.Response;

/**
 * Created by rafal on 03.07.2023
 *
 * @author : rafal
 * @date : 03.07.2023
 * @project : Battleship
 */
public interface CommunicatorInterface {
    void sendMessage(String message);
    Response getInvitationResponse() throws IOException;
    Request getShotRequest() throws IOException;
    Response getShotResult() throws IOException;
}