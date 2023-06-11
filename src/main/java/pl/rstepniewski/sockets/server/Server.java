package pl.rstepniewski.sockets.server;

import java.io.IOException;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        ServerService serverService = new ServerService(server);
        ServerCommunicator serverComunicator = new ServerCommunicator(serverService);
        serverComunicator.startComunication();
    }

}
