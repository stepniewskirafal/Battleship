package pl.rstepniewski.sockets.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);
    public static void main(String[] args) throws IOException {
        ClientService clientService = new ClientService();
        ClientCommunicatorImpl communicator = new ClientCommunicatorImpl(clientService);
        ClientController clientController = new ClientController(communicator);
        clientController.playGame();
    }
}
