package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class GameController {

    private final int NUMBER_OF_ALL_SHIPS = 10;
    private final int NUMBER_OF_1_MASTE_SHIPS = 4;
    private final int NUMBER_OF_2_MASTE_SHIPS = 3;
    private final int NUMBER_OF_3_MASTE_SHIPS = 2;
    private final int NUMBER_OF_4_MASTE_SHIPS = 1;

    private final UserInterface userInterface;
    private final Board board;

    public GameController(UserInterface userInterface, Board board) {
        this.userInterface = userInterface;
        this.board = board;
    }

    public void runGame(){
        userInterface.printProperty("game.start");

        List<Ship> shipCoordinatesFromUser = getShipCoordinatesFromUser();
        board.markShipPosition(shipCoordinatesFromUser);
        board.printBoard();
    }

    private List<Ship> getShipCoordinatesFromUser() {
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

            userInterface.printProperty("ship.oneMast.desc");
            while(shipCounter < NUMBER_OF_1_MASTE_SHIPS){
                userInterface.printProperty("ship.oneMast.coordinates");
                userInterface.printText("  -one mast ship nr." + (shipCounter + 1) );
                Point point = new Point(userInterface.readText());
                createShipAndAddToList(shipList, point, point);
                shipCounter++;
            }
           /* shipCounter=0;
            userInterface.printProperty("ship.twoMast.desc");
            while(shipCounter < NUMBER_OF_2_MASTE_SHIPS){
                userInterface.printProperty("ship.twoMast.bow.coordinates");
                userInterface.printText("  -two mast ship nr." + (shipCounter + 1) );
                Point pointBow = new Point(userInterface.readText());
                userInterface.printProperty("ship.twoMast.stern.coordinates");
                userInterface.printText("  -two mast ship nr." + (shipCounter + 1) );
                Point pointStern = new Point(userInterface.readText());
                createShipAndAddToList(shipList, pointBow, pointStern);
                shipCounter++;
            }
            shipCounter=0;
            userInterface.printProperty("ship.treeMast.desc");
            while(shipCounter < NUMBER_OF_3_MASTE_SHIPS){
                userInterface.printProperty("ship.treeMast.bow.coordinates");
                userInterface.printText("  -tree mast ship nr." + (shipCounter + 1) );
                Point pointBow = new Point(userInterface.readText());
                userInterface.printProperty("ship.treeMast.stern.coordinates");
                userInterface.printText("  -tree mast ship nr." + (shipCounter + 1) );
                Point pointStern = new Point(userInterface.readText());
                createShipAndAddToList(shipList, pointBow, pointStern);
                shipCounter++;
            }
            shipCounter=0;
            userInterface.printProperty("ship.fourMast.desc");
            while(shipCounter < NUMBER_OF_4_MASTE_SHIPS){
                userInterface.printProperty("ship.fourMast.bow.coordinates");
                userInterface.printText("  -four mast ship nr." + (shipCounter + 1) );
                Point pointBow = new Point(userInterface.readText());
                userInterface.printProperty("ship.fourMast.stern.coordinates");
                userInterface.printText("  -four mast ship nr." + (shipCounter + 1) );
                Point pointStern = new Point(userInterface.readText());
                createShipAndAddToList(shipList, pointBow, pointStern);
                shipCounter++;
            }*/

        return shipList;
    }

    private static void createShipAndAddToList(List<Ship> shipList, Point pointBow, Point pointStern) {
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(pointBow, pointStern));
        shipList.add( new Ship(points) );
    }


}
