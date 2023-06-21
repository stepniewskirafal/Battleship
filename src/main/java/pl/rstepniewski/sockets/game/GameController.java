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
    private final int LENGTH_OF_2_MASTE_SHIPS = 2;
    private final int LENGTH_OF_3_MASTE_SHIPS = 3;
    private final int LENGTH_OF_4_MASTE_SHIPS = 4;

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
                ArrayList<Point> points = new ArrayList<>(Arrays.asList(point, point));
                if(!checkIfCoordinatesCorrect(shipList, points)){
                    createShipAndAddToList(shipList, points);
                    shipCounter++;
                }
            }
            shipCounter=0;                                                    // pobranie koordynat do funckji i sprawdzaj czy zakres liczby i litery sie zgadza!!!
            userInterface.printProperty("ship.twoMast.desc");
            while(shipCounter < NUMBER_OF_2_MASTE_SHIPS){
                userInterface.printProperty("ship.twoMast.bow.coordinates");
                userInterface.printText("  -two mast ship nr." + (shipCounter + 1) );
                Point pointBow = new Point(userInterface.readText());
                userInterface.printProperty("ship.twoMast.stern.coordinates");
                userInterface.printText("  -two mast ship nr." + (shipCounter + 1) );
                Point pointStern = new Point(userInterface.readText());
                ArrayList<Point> newShipPoints = new ArrayList<>(Arrays.asList(pointBow, pointStern));
                if(checkIfShipLengthIsCorrect(newShipPoints, LENGTH_OF_2_MASTE_SHIPS )) {
                    if (!checkIfCoordinatesCorrect(shipList, newShipPoints)) {
                        createShipAndAddToList(shipList, newShipPoints);
                        shipCounter++;
                    }
                }
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
                ArrayList<Point> newShipPoints = new ArrayList<>(Arrays.asList(pointBow, pointStern));
                newShipPoints = fillGaps(newShipPoints);
                if(checkIfShipLengthIsCorrect(newShipPoints, LENGTH_OF_3_MASTE_SHIPS )) {
                    if (!checkIfCoordinatesCorrect(shipList, newShipPoints)) {
                        createShipAndAddToList(shipList, newShipPoints);
                        shipCounter++;
                    }
                }
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
                ArrayList<Point> newShipPoints = new ArrayList<>(Arrays.asList(pointBow, pointStern));
                newShipPoints = fillGaps(newShipPoints);
                if(checkIfShipLengthIsCorrect(newShipPoints, LENGTH_OF_4_MASTE_SHIPS )) {
                    if (!checkIfCoordinatesCorrect(shipList, newShipPoints)) {
                        createShipAndAddToList(shipList, newShipPoints);
                        shipCounter++;
                    }
                }
            }
        return shipList;
    }

    private ArrayList<Point> fillGaps(ArrayList<Point> newShipPoints) {
        ArrayList<Point> filledNewShipPoints = newShipPoints;
        Point pointA = newShipPoints.get(0);
        Point pointB = newShipPoints.get(1);

        if(pointA.x() == pointB.x()){
            int startY = Math.min(pointA.y(), pointB.y());
            int stopY = Math.max(pointA.y(), pointB.y());

            for(int i=startY+1; i<stopY; i++){
                filledNewShipPoints.add(new Point(pointA.x(), i));
            }
        }else {
            int startX = Math.min(pointA.x(), pointB.x());
            int stopX = Math.max(pointA.x(), pointB.x());

            for(int i=startX+1; i<stopX; i++){
                filledNewShipPoints.add(new Point(i, pointA.y()));
            }
        }
        return filledNewShipPoints;
    }

    private boolean checkIfShipLengthIsCorrect(ArrayList<Point> newShipPoints, int shipLength) {
        return Ship.getLength(newShipPoints) == shipLength ? true : false;
    }

    private boolean checkIfCoordinatesCorrect(List<Ship> shipList, ArrayList<Point> newShipPoints) {
        return shipList.stream()
                .anyMatch(x-> x.isNeighbour(newShipPoints));
    }

    private static void createShipAndAddToList(List<Ship> shipList, ArrayList<Point> points) {
        shipList.add( new Ship(points) );
    }


}
