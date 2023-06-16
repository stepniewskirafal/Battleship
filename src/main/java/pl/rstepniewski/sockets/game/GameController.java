package pl.rstepniewski.sockets.game;

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

        String[] shipCoordinates = getShipCoordinatesFromUser();
        ShipPosition shipPosition = Board.convertShipsPosition(shipCoordinates);
        Board.markShipPosition(shipPosition);
    }

    private String[] getShipCoordinatesFromUser() {

        String[] shipCoordinates = new String[NUMBER_OF_ALL_SHIPS];
        String shipTempCoordinates;
        int shipCounter = 0;

        for(int i=0; i<NUMBER_OF_ALL_SHIPS; i++){

            while(shipCounter < NUMBER_OF_1_MASTE_SHIPS){
                userInterface.printProperty("ship.oneMast.bow.coordinates");
                shipTempCoordinates = userInterface.readText();
                userInterface.printProperty("ship.oneMast.stern.coordinates");
                shipTempCoordinates += userInterface.readText();
                shipCoordinates[i] = shipTempCoordinates;
                shipTempCoordinates = "";
                shipCounter++;
            }
           /* shipCounter=0;
            while(shipCounter < NUMBER_OF_2_MASTE_SHIPS){
                userInterface.printProperty("ship.twoMast.bow.coordinates");
                shipTempCoordinates = userInterface.readText();
                userInterface.printProperty("ship.twoMast.stern.coordinates");
                shipTempCoordinates += userInterface.readText();
                shipCoordinates[i] = shipTempCoordinates;
                shipTempCoordinates = "";
                shipCounter++;
            }
            shipCounter=0;
            while(shipCounter < NUMBER_OF_3_MASTE_SHIPS){
                userInterface.printProperty("ship.treeMast.bow.coordinates");
                shipTempCoordinates = userInterface.readText();
                userInterface.printProperty("ship.treeMast.stern.coordinates");
                shipTempCoordinates += userInterface.readText();
                shipCoordinates[i] = shipTempCoordinates;
                shipTempCoordinates = "";
                shipCounter++;
            }
            shipCounter=0;
            while(shipCounter < NUMBER_OF_4_MASTE_SHIPS){
                userInterface.printProperty("ship.fourMast.bow.coordinates");
                shipTempCoordinates = userInterface.readText();
                userInterface.printProperty("ship.fourMast.stern.coordinates");
                shipTempCoordinates += userInterface.readText();
                shipCoordinates[i] = shipTempCoordinates;
                shipTempCoordinates = "";
                shipCounter++;
            }*/
        }
        return shipCoordinates;
    }




}
