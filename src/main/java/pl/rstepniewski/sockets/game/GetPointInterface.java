package pl.rstepniewski.sockets.game;

import java.util.Random;

public interface GetPointInterface {

    Random random = new Random();
    static Point getNewUserPoint(){
        String userShot;
        do{
            userShot = UserInterface.readText();
        }while(!checkUserPoint(userShot));
        return new Point(userShot);
    }

    static boolean checkUserPoint(String shot) {
        char letterChar       = shot.charAt(0);
        char firstNumberChar  = shot.charAt(1);
        char secondNumberChar = shot.length() == 3 ? shot.charAt(2) : '0';

        return (letterChar >= 'A' && letterChar <= 'J' || letterChar >= 'a' && letterChar <= 'j' )
                && firstNumberChar >= '0' && firstNumberChar <= '9' && secondNumberChar == '0'? true : false;
    }
    
    static Point getNewRandomPoint(){
        int row = random.nextInt(10);
        int column = random.nextInt(10);
        return new Point(row, column);
    }

    public static void main(String[] args) {
        Point newRandomShot = GetPointInterface.getNewRandomPoint();
        System.out.println(newRandomShot);
    }
}