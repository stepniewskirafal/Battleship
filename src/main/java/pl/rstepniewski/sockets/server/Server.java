package pl.rstepniewski.sockets.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    public static void main(String[] args) throws IOException {
        logger.info("Starting Battleship application");
        Server server = new Server();
        logger.info("Starting serwer");
        ServerService serverService = new ServerService(server);
        ServerCommunicator serverComunicator = new ServerCommunicator(serverService);
        serverComunicator.handleGame();


    }

}
