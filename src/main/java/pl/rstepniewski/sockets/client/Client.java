package pl.rstepniewski.sockets.client;
import java.io.IOException;

/**
Created by rafal on 09.06.2023
@author : rafal
@date : 09.06.2023
@project : Battleship
*/
public class Client {
    public static void main(String[] args) throws IOException {
        ClientController clientController = new ClientController();
        clientController.playGame();
    }
}
