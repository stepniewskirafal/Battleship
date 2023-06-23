package pl.rstepniewski.sockets.game;

public interface ShotInterface {
    static Point getNewShot(){
        UserInterface.printProperty("shoot.prompt");
        return new Point(UserInterface.readText());
    }
}
