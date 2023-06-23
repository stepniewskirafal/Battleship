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
public class BoardController {
    private static final int ONE_MAST_SHIP_COUNT = 4;
    private static final int TWO_MAST_SHIP_COUNT = 3;
    private static final int THREE_MAST_SHIP_COUNT = 2;
    private static final int FOUR_MAST_SHIP_COUNT = 1;
    private static final int ONE_MAST_SHIP_LENGTH = 1;
    private static final int TWO_MAST_SHIP_LENGTH = 2;
    private static final int THREE_MAST_SHIP_LENGTH = 3;
    private static final int FOUR_MAST_SHIP_LENGTH = 4;

    private final Board board;

    public BoardController(Board board) {
        this.board = board;
    }

    public void initialiseBord(){
        UserInterface.printProperty("game.start");

/*        List<Ship> shipCoordinatesFromUser = getShipCoordinatesFromUser();
        board.markShipPosition(shipCoordinatesFromUser);*/
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

        UserInterface.printProperty("ship.oneMast.desc");
        board.printBoard();
        while(shipCounter < ONE_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.oneMast.coordinates");
            UserInterface.printText("-one mast ship nr."+(shipCounter+1));
            Point point=new Point(UserInterface.readText());
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

        UserInterface.printProperty("ship.twoMast.desc");
        board.printBoard();
        while(shipCounter < TWO_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.twoMast.bow.coordinates");
            UserInterface.printText("-two mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.twoMast.stern.coordinates");
            UserInterface.printText("-two mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
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

        UserInterface.printProperty("ship.treeMast.desc");
        board.printBoard();
        while(shipCounter < THREE_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.treeMast.bow.coordinates");
            UserInterface.printText("-tree mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.treeMast.stern.coordinates");
            UserInterface.printText("-tree mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
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

        UserInterface.printProperty("ship.fourMast.desc");
        board.printBoard();
        while(shipCounter < FOUR_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.fourMast.bow.coordinates");
            UserInterface.printText("-four mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.fourMast.stern.coordinates");
            UserInterface.printText("-four mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
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

    public void markShotHit(Point shot){
        board.markShot(shot, BoardCellStatus.HIT);
        board.printBoard();
    }

    public void markShotMiss(Point shot){
        board.markShot(shot, BoardCellStatus.MISS);
        board.printBoard();
    }

    public void markShotSinking(Point shot){
        board.markShot(shot, BoardCellStatus.HIT);
        board.printBoard();
    }
}