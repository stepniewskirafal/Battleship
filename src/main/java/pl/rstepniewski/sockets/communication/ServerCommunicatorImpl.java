package pl.rstepniewski.sockets.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.jsonCommunication.message.Message;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;
import pl.rstepniewski.sockets.server.ServerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by rafal on 03.07.2023
 *
 * @author : rafal
 * @date : 03.07.2023
 * @project : Battleship
 */
public class ServerCommunicatorImpl implements CommunicatorInterface {
    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;
    private final ObjectMapper objectMapper;
    private final ServerService serverService;
    public ServerCommunicatorImpl(ServerService serverService) {
        this.serverService = serverService;
        this.printWriter = serverService.getPrintWriter();
        this.bufferedReader = serverService.getBufferedReader();
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public Message getClientMessage() throws IOException {
        String jsonString = getJsonString();
        Message message = objectMapper.readValue(jsonString, Message.class);
        return message;
    }
    @Override
    public String getJsonString() throws IOException {
        String responseJson = bufferedReader.readLine();
        return responseJson;
    }
    @Override
    public Response getResponse(String jsonString) throws IOException {
        return objectMapper.readValue(jsonString, Response.class);
    }
    @Override
    public Request getRequest(String jsonString) throws JsonProcessingException {
        Request request = objectMapper.readValue(jsonString, Request.class);
        return request;
    }
    @Override
    public Response getResponse() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }
    @Override
    public Request getRequest() throws IOException {
        String jsonString = bufferedReader.readLine();
        Request request = objectMapper.readValue(jsonString, Request.class);
        return request;
    }
    @Override
    public void sendResponse(Response response) throws JsonProcessingException {
        String responseJson = objectMapper.writeValueAsString(response);
        printWriter.println(responseJson);
    }
    @Override
    public void sendRequest(Request request) throws JsonProcessingException {
        String requestJson = objectMapper.writeValueAsString(request);
        printWriter.println(requestJson);
    }
}














