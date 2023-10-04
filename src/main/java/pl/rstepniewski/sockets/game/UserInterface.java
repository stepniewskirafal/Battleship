package pl.rstepniewski.sockets.game;

import pl.rstepniewski.sockets.propertiesUtil.PropertiesUtil;

import java.util.Scanner;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */

public interface UserInterface {
    Scanner scanner = new Scanner(System.in);

    static void printText(String string) {
        System.out.println(string);
    }

    static void printProperty(String string) {
        System.out.println(PropertiesUtil.getString(string));
    }

    static String readText() {
        return scanner.nextLine();
    }

    static int readInt() {
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    static void printEnter(int nuberOfEnters) {
        if (nuberOfEnters > 0) {
            StringBuilder emptyLines = new StringBuilder();
            for (int i = 0; i < nuberOfEnters; i++) {
                emptyLines.append(System.lineSeparator());
            }
            System.out.print(emptyLines);
        }

    }
}