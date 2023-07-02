package pl.rstepniewski.sockets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rstepniewski.sockets.jsonCommunication.Request;
import pl.rstepniewski.sockets.jsonCommunication.Response;

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
    public void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }

    @Override
    public Response getInvitationResponse() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }

    @Override
    public Request getShotRequest() throws IOException {
        String jsonString = bufferedReader.readLine();
        return objectMapper.readValue(jsonString, Request.class);
    }

    @Override
    public Response getShotResult() throws IOException {
        String responseJson = bufferedReader.readLine();
        return objectMapper.readValue(responseJson, Response.class);
    }
}