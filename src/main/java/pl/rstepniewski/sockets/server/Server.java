package pl.rstepniewski.sockets.server;

import pl.rstepniewski.sockets.client.ClientCommunicatorImpl;

import java.io.IOException;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Server {
    public static void main(String[] args) throws IOException {
        ServerService serverService = new ServerService();
        ServerCommunicatorImpl communicator = new ServerCommunicatorImpl(serverService);
        ServerConroller serverConroller = new ServerConroller(communicator);
        serverConroller.handleGame();
    }

}
