package src.main.java.pl.stepniewski.sockets.server;

import java.io.*;

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
        ServerComunicator serverComunicator = new ServerComunicator(serverService);
        serverComunicator.startComunication();
    }

}
