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
public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public UserInterface() {
    }

    public static void printText(String string) {
        System.out.println(string);
    }

    public static void printProperty(String string) {
        System.out.println(PropertiesUtil.getString(string));
    }

    public static String readText() {
        return scanner.nextLine();
    }

    public static String printAndReadText(String string) {
        printText(string);
        return scanner.nextLine();
    }
}
