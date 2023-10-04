package pl.rstepniewski.sockets.controller;

import pl.rstepniewski.sockets.controller.client.ClientAIController;
import pl.rstepniewski.sockets.controller.client.ClientController;
import pl.rstepniewski.sockets.controller.server.ServerConroller;
import pl.rstepniewski.sockets.game.UserInterface;
import pl.rstepniewski.sockets.jsonCommunication.MenuCode;

import java.io.IOException;

public class GameControllerUtil implements UserInterface {

    private GameControllerUtil() {
    }

    public static void main(String[] args) throws IOException {
        MenuCode choice;

        do {
            printMenu();
            choice = getMenuChoice();
            switch (choice) {
                case CLIENT_CONSOLE:
                    handleClientConsole();
                    break;
                case CLIENT_AI:
                    handleClientAI();
                    break;
                case SERVER_AI:
                    handleServerAI();
                    break;
                default:
                    printInvalidChoiceMessage();
            }
        } while (choice != MenuCode.EXIT_GAME);
    }

    private static void printMenu() {
        UserInterface.printProperty("game.menu");
        UserInterface.printProperty("game.menu.options");
    }

    private static MenuCode getMenuChoice() {
        return MenuCode.buildMenuCodeFromInt(UserInterface.readInt());
    }

    private static void handleClientConsole() throws IOException {
        ClientController clientController = new ClientController();
        clientController.playGame();
        UserInterface.printProperty("game.menu.options.history");
        MenuCode historyChoice = getMenuChoice();
        if (historyChoice == MenuCode.GAME_HISTORY) {
            UserInterface.printProperty("game.menu.options.history.speed");
            int secondNumber = UserInterface.readInt();
            clientController.viewGameHistory(secondNumber * 1000);
        }
        clientController.endGame();
    }

    private static void handleClientAI() throws IOException {
        ClientAIController clientAIController = new ClientAIController();
        clientAIController.playGame();
        clientAIController.endGame();
    }

    private static void handleServerAI() throws IOException {
        ServerConroller serverConroller = new ServerConroller();
        serverConroller.handleGame();
        serverConroller.endGame();
    }

    private static void printInvalidChoiceMessage() {
        UserInterface.printProperty("game.menu.again");
    }
}