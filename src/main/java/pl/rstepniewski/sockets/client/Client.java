package pl.rstepniewski.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Client {
    static PrintWriter printWriter;
    static BufferedReader bufferedReader;
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        ClientService clientService = new ClientService(client);
        ClientCommunicator clientComunicator = new ClientCommunicator(clientService);
        clientComunicator.startCommunication();

    }

}
