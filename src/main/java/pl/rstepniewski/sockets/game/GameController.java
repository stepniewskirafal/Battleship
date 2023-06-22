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
    private static final int ONE_MAST_SHIP_COUNT = 4;
    private static final int TWO_MAST_SHIP_COUNT = 3;
    private static final int THREE_MAST_SHIP_COUNT = 2;
    private static final int FOUR_MAST_SHIP_COUNT = 1;
    private static final int ONE_MAST_SHIP_LENGTH = 1;
    private static final int TWO_MAST_SHIP_LENGTH = 2;
    private static final int THREE_MAST_SHIP_LENGTH = 3;
    private static final int FOUR_MAST_SHIP_LENGTH = 4;

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

    private List<Ship> getShipCoordinatesFromUser(){
        List<Ship> allShipsFromUser = new ArrayList<>();

        allShipsFromUser.addAll(getCoordinatesForOneMastShips());
        allShipsFromUser.addAll(getCoordinatesForTwoMastShips());
        allShipsFromUser.addAll(getCoordinatesForThreeMastShips());
        allShipsFromUser.addAll(getCoordinatesForFourMastShips());

        return allShipsFromUser;
    }

    private List<Ship> getCoordinatesForOneMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        userInterface.printProperty("ship.oneMast.desc");
        board.printBoard();
        while(shipCounter < ONE_MAST_SHIP_COUNT){
            userInterface.printProperty("ship.oneMast.coordinates");
            userInterface.printText("-onemastshipnr."+(shipCounter+1));
            Point point=new Point(userInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(point, point);

            if(validateCoordinates(shipList, newShipPoints, ONE_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints);
                shipCounter++;
            }
        }
        return shipList;
    }

    private List<Ship> getCoordinatesForTwoMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        userInterface.printProperty("ship.twoMast.desc");
        board.printBoard();
        while(shipCounter < TWO_MAST_SHIP_COUNT){
            userInterface.printProperty("ship.twoMast.bow.coordinates");
            userInterface.printText("-twomastshipnr."+(shipCounter+1));
            Point pointBow=new Point(userInterface.readText());
            userInterface.printProperty("ship.twoMast.stern.coordinates");
            userInterface.printText("-twomastshipnr."+(shipCounter+1));
            Point pointStern=new Point(userInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, TWO_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints);
                shipCounter++;
            }
        }

        return shipList;
    }

    private List<Ship> getCoordinatesForThreeMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        userInterface.printProperty("ship.treeMast.desc");
        board.printBoard();
        while(shipCounter < THREE_MAST_SHIP_COUNT){
            userInterface.printProperty("ship.treeMast.bow.coordinates");
            userInterface.printText("-treemastshipnr."+(shipCounter+1));
            Point pointBow=new Point(userInterface.readText());
            userInterface.printProperty("ship.treeMast.stern.coordinates");
            userInterface.printText("-treemastshipnr."+(shipCounter+1));
            Point pointStern=new Point(userInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, THREE_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints);
                shipCounter++;
            }
        }

        return shipList;
    }

    private List<Ship> getCoordinatesForFourMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        userInterface.printProperty("ship.fourMast.desc");
        board.printBoard();
        while(shipCounter < FOUR_MAST_SHIP_COUNT){
            userInterface.printProperty("ship.fourMast.bow.coordinates");
            userInterface.printText("-fourmastshipnr."+(shipCounter+1));
            Point pointBow=new Point(userInterface.readText());
            userInterface.printProperty("ship.fourMast.stern.coordinates");
            userInterface.printText("-fourmastshipnr."+(shipCounter+1));
            Point pointStern=new Point(userInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, FOUR_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints);
                shipCounter++;
            }
        }

        return shipList;
    }

    private boolean validateCoordinates(List<Ship> shipList, ArrayList<Point> newShipPoints, int shipLength) {
        return checkIfShipLengthIsCorrect(newShipPoints, shipLength) &&
                !checkIfCoordinatesCorrect(shipList, newShipPoints);
    }

    private boolean checkIfShipLengthIsCorrect(ArrayList<Point> newShipPoints, int shipLength) {
        return Ship.getLength(newShipPoints) == shipLength;
    }

    private boolean checkIfCoordinatesCorrect(List<Ship> shipList, ArrayList<Point> newShipPoints) {
        return shipList.stream()
                .anyMatch(ship -> ship.isNeighbour(newShipPoints));
    }

    private static void createShipAndAddToList(List<Ship> shipList, ArrayList<Point> points) {
        shipList.add( new Ship(points) );
    }

    private ArrayList<Point> createListOfShipPoints(Point pointBow, Point pointStern) {
        ArrayList<Point> newShipPoints=new ArrayList<>(Arrays.asList(pointBow, pointStern));
        newShipPoints=fillGaps(newShipPoints);
        return newShipPoints;
    }

    private ArrayList<Point> fillGaps(ArrayList<Point> newShipPoints) {
        ArrayList<Point> filledNewShipPoints = new ArrayList<>(newShipPoints);
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
}