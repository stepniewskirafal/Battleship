package pl.rstepniewski.sockets.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

import java.io.IOException;

/**
 * Created by rafal on 03.07.2023
 *
 * @author : rafal
 * @date : 03.07.2023
 * @project : Battleship
 */
public interface CommunicatorInterface {
    String getClientMessage() throws IOException;

    String getJsonString() throws IOException;

    Response getResponse() throws IOException;

    void sendResponse(Response response) throws JsonProcessingException;

    void sendRequest(Request request) throws JsonProcessingException;
}