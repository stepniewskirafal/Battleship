package pl.rstepniewski.sockets.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.controller.server.ServerService;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

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

    public void stopCommunicator() {
        serverService.closeConnection();
    }

    @Override
    public String getClientMessage() throws IOException {
        return getJsonString();
    }

    @Override
    public String getJsonString() throws IOException {
        String responseJson;
        try {
            responseJson = bufferedReader.readLine();
        } catch (IOException e) {
            responseJson = null;
        }

        return responseJson;
    }

    @Override
    public Response getResponse() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }

    @Override
    public void sendResponse(Response response) throws JsonProcessingException {
        String responseJson = objectMapper.writeValueAsString(response);
        printWriter.println(responseJson);
    }

    @Override
    public void sendRequest(Request request) {
    }
}














