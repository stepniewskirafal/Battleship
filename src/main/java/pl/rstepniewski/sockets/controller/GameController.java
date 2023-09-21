package pl.rstepniewski.sockets.controller;

import pl.rstepniewski.sockets.controller.client.ClientController;
import pl.rstepniewski.sockets.controller.server.ServerConroller;
import pl.rstepniewski.sockets.game.UserInterface;

import java.io.IOException;
import java.util.Scanner;

public class GameController implements UserInterface {

    public static void main(String[] args) throws IOException {
        int chose;

        do{
            UserInterface.printText("Wybierz zadanie: " );
            UserInterface.printText("1: klient konsolowy, 2: klient AI, 3: server AI, 0: zakończ");
            chose = UserInterface.readInt();
            switch (chose){
                case 1:
                    ClientController clientController = new ClientController();
                    clientController.playGame();
                    UserInterface.printText("Czy chcesz zobaczyć przebieg gry: 1/0");
                    chose = UserInterface.readInt();
                    if (chose == 1) {
                        UserInterface.printText("Wybierz odstęp czasowy pomiędzy wyświetlanymi kolejnymi turami. Podaj likość sekund w postaci liczby całkowitej");
                        chose = UserInterface.readInt();
                        clientController.viewGameHistory(chose*1000);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    ServerConroller serverConroller = new ServerConroller();
                    serverConroller.handleGame();
                    break;
                default:
                    UserInterface.printText("Wybierz jeszcze raz");
            }
        }while(chose != 0);
    }
}


/*
*       System.out.println("*******************************************");
        System.out.println("*     WITAJ KAPITANIE W GRZE STATKI!     *");
        System.out.println("*******************************************");
        System.out.println("*    Przygotuj się na wielką bitwę na    *");
        System.out.println("*            pełnym morzu chaosu!        *");
        System.out.println("*******************************************");
        System.out.println("*      Twoim celem jest zatopienie        *");
        System.out.println("*      wszystkich wrogich statków,        *");
        System.out.println("*          zanim oni zatopią Ciebie!      *");
        System.out.println("*******************************************");
        System.out.println("*   Rozpocznij bitwę i pokaż, kto jest    *");
        System.out.println("*        prawdziwym panem mórz!           *");
        System.out.println("*******************************************");
*
*
*
* */