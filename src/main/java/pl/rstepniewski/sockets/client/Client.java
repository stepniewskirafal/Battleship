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
        logger.info("Starting Battleship application");
        Client client = new Client();
        ClientService clientService = new ClientService(client);
        ClientCommunicator clientCommunicator = new ClientCommunicator(clientService);
        logger.info("Starting Battleship game");
        clientCommunicator.playGame();
    }
}
