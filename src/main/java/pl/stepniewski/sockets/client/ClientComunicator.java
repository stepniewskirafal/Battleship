package src.main.java.pl.stepniewski.sockets.client;

import java.util.Scanner;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientComunicator {

    private final ClientService serverService;

    public ClientComunicator(ClientService serverService) {
        this.serverService = serverService;
    }

    public void startComunication() {
        final Scanner scanner = new Scanner(System.in);
        serverService.getPrintWriter().println(scanner.next());
    }
}
