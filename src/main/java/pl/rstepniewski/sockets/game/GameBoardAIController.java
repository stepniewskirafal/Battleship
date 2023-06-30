package pl.rstepniewski.sockets.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.server.ServerCommunicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class GameBoardAIController {
    private static final int ONE_MAST_SHIP_COUNT = 4;
    private static final int TWO_MAST_SHIP_COUNT = 3;
    private static final int THREE_MAST_SHIP_COUNT = 2;
    private static final int FOUR_MAST_SHIP_COUNT = 1;
    private static final int ONE_MAST_SHIP_LENGTH = 1;
    private static final int TWO_MAST_SHIP_LENGTH = 2;
    private static final int THREE_MAST_SHIP_LENGTH = 3;
    private static final int FOUR_MAST_SHIP_LENGTH = 4;

    private static final Logger logger = LogManager.getLogger(GameBoardAIController.class);

    private final GameBoard gameBoard;
    private Fleet fleet;

    public GameBoardAIController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void initialiseBord(){
        logger.info("Server initialized it's gameboards succesfully");

/*        Fleet fleet = new Fleet(getShipCoordinatesFromUser());

        board.markShipPosition(fleet.getFleet());
        board.printBoard();*/

        Ship ship = new Ship(Arrays.asList(new Point("C1"), new Point("C2")), 2);
        List<Ship> list = Arrays.asList(ship);
        fleet = new Fleet(list);

        gameBoard.markShipPosition(fleet.getFleet());
        gameBoard.printBoards();
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
        gameBoard.printShipBoard();
        while(shipCounter < ONE_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.oneMast.coordinates");
            UserInterface.printText("-one mast ship nr."+(shipCounter+1));
            Point point=new Point(UserInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(point, point);

            if(validateCoordinates(shipList, newShipPoints, ONE_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints, ONE_MAST_SHIP_LENGTH);
                shipCounter++;
            }
        }
        return shipList;
    }

    private List<Ship> getCoordinatesForTwoMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        UserInterface.printProperty("ship.twoMast.desc");
        gameBoard.printShipBoard();
        while(shipCounter < TWO_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.twoMast.bow.coordinates");
            UserInterface.printText("-two mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.twoMast.stern.coordinates");
            UserInterface.printText("-two mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, TWO_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints, TWO_MAST_SHIP_LENGTH);
                shipCounter++;
            }
        }

        return shipList;
    }

    private List<Ship> getCoordinatesForThreeMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        UserInterface.printProperty("ship.treeMast.desc");
        gameBoard.printShipBoard();
        while(shipCounter < THREE_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.treeMast.bow.coordinates");
            UserInterface.printText("-tree mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.treeMast.stern.coordinates");
            UserInterface.printText("-tree mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, THREE_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints, THREE_MAST_SHIP_LENGTH);
                shipCounter++;
            }
        }
        return shipList;
    }

    private List<Ship> getCoordinatesForFourMastShips(){
        List<Ship> shipList = new ArrayList<>();
        int shipCounter = 0;

        UserInterface.printProperty("ship.fourMast.desc");
        gameBoard.printShipBoard();
        while(shipCounter < FOUR_MAST_SHIP_COUNT){
            UserInterface.printProperty("ship.fourMast.bow.coordinates");
            UserInterface.printText("-four mast ship nr."+(shipCounter+1));
            Point pointBow=new Point(UserInterface.readText());
            UserInterface.printProperty("ship.fourMast.stern.coordinates");
            UserInterface.printText("-four mast ship nr."+(shipCounter+1));
            Point pointStern=new Point(UserInterface.readText());
            ArrayList<Point> newShipPoints = createListOfShipPoints(pointBow, pointStern);

            if(validateCoordinates(shipList, newShipPoints, FOUR_MAST_SHIP_LENGTH)){
                createShipAndAddToList(shipList,newShipPoints, FOUR_MAST_SHIP_LENGTH);
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

    private static void createShipAndAddToList(List<Ship> shipList, ArrayList<Point> points, int lenght) {
        shipList.add( new Ship(points, lenght) );
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

        if(pointA.getX() == pointB.getX()){
            int startY = Math.min(pointA.getY(), pointB.getY());
            int stopY = Math.max(pointA.getY(), pointB.getY());

            for(int i=startY+1; i<stopY; i++){
                filledNewShipPoints.add(new Point(pointA.getX(), i));
            }
        }else {
            int startX = Math.min(pointA.getX(), pointB.getX());
            int stopX = Math.max(pointA.getX(), pointB.getX());

            for(int i=startX+1; i<stopX; i++){
                filledNewShipPoints.add(new Point(i, pointA.getY()));
            }
        }

        return filledNewShipPoints;
    }

    public void markHitOnShortBoard(Point shot){
        gameBoard.markHitOnShortBoard(shot, BoardCellStatus.HIT);
        UserInterface.printProperty("shoot.hit");
        gameBoard.printBoards();
    }

    public void markMissOnShortBoard(Point shot){
        gameBoard.markHitOnShortBoard(shot, BoardCellStatus.MISS);
        UserInterface.printProperty("shoot.miss");
        gameBoard.printBoards();
    }

    public void markSinkingOnShortBoard(Point shot){
        markHitOnShortBoard(shot);
        UserInterface.printProperty("shoot.sunk");
        gameBoard.printBoards();
    }

    public boolean isFleetAlive(){
        return !fleet.isFleetSunk();
    }

    public boolean isShipSinking(Point point){
        Ship ship = fleet.findShip(point).get();
        return ship.isSinking();
    }

    public boolean isShotHit(Point shot){
        Optional<Ship> ship = fleet.findShip(shot);
        return ship.isPresent();
    }

    public void markHitOnShipBoard(Point shot) {
        Ship ship = fleet.findShip(shot).orElseThrow();

        Point hitPoint = ship.getPosition().stream()
                .filter(position -> position.equals(shot))
                .findFirst()
                .orElseThrow();

        hitPoint.setPointSinking(true);

        int index = ship.getPosition().indexOf(hitPoint);
        ship.getPosition().set(index, hitPoint);

        int shipIndex = fleet.getFleet().indexOf(ship);
        fleet.getFleet().set(shipIndex, ship);
    }
}