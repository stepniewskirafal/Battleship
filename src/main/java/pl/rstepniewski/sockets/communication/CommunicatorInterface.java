package pl.rstepniewski.sockets.communication;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

/**
 * Created by rafal on 03.07.2023
 *
 * @author : rafal
 * @date : 03.07.2023
 * @project : Battleship
 */
public interface CommunicatorInterface {
    String getJsonString() throws IOException;
    String getClientMessage() throws IOException;
    Response getResponse(String jsonString) throws IOException;
    Request getRequest(String jsonString) throws IOException;
    Response getResponse() throws IOException;
    Request getRequest() throws IOException;
    void sendResponse(Response response) throws JsonProcessingException;
    void sendRequest(Request request) throws JsonProcessingException;
}