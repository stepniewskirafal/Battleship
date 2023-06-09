package pl.rstepniewski.sockets.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ServerComunicator {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ServerService serverService;

    public ServerComunicator(ServerService serverService) {
        this.serverService = serverService;
    }

    public void startComunication() {
        final Scanner scanner = new Scanner(System.in);
        serverService.getPrintWriter().println(scanner.next());
    }
}
