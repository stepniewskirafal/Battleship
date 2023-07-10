package pl.rstepniewski.sockets.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.client.ClientService;
import pl.rstepniewski.sockets.jsonCommunication.message.Request;
import pl.rstepniewski.sockets.jsonCommunication.message.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Created by rafal on 02.07.2023
 *
 * @author : rafal
 * @date : 02.07.2023
 * @project : Battleship
 */
public class ClientCommunicatorImpl implements CommunicatorInterface {
    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;
    private final ObjectMapper objectMapper;
    private final ClientService clientService;

    public ClientCommunicatorImpl(ClientService clientService) {
        this.clientService = clientService;
        this.printWriter = clientService.getPrintWriter();
        this.bufferedReader = clientService.getBufferedReader();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getJsonString() throws IOException {
        String responseJson = bufferedReader.readLine();
        return responseJson;
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