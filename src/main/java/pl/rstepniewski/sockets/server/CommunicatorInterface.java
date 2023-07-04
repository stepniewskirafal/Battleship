package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.Response;

import java.io.IOException;

/**
 * Created by rafal on 03.07.2023
 *
 * @author : rafal
 * @date : 03.07.2023
 * @project : Battleship
 */
public interface CommunicatorInterface {
    Response getResponse() throws IOException;

    Request getRequest() throws IOException;

    void sendResponse(Response response) throws JsonProcessingException;

    void sendRequest(Request request) throws JsonProcessingException;
}