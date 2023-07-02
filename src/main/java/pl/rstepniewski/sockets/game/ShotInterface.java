package pl.rstepniewski.sockets.game;

import java.util.Random;

public interface ShotInterface {

    Random random = new Random();
    static Point getNewUserShot(){
        UserInterface.printProperty("shoot.prompt");
        return new Point(UserInterface.readText());
    }

    static Point getNewRandomShot(){
        int row = random.nextInt(10);
        int column = random.nextInt(10);
        return new Point(row, column);
    }

    public static void main(String[] args) {
        Point newRandomShot = ShotInterface.getNewRandomShot();
        System.out.println(newRandomShot);
    }
}
