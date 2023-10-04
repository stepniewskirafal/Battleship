package pl.rstepniewski.sockets.game.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rstepniewski.sockets.dto.PointDto;
import pl.rstepniewski.sockets.dto.ShipDto;
import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.UserInterface;
import pl.rstepniewski.sockets.game.fleet.Fleet;
import pl.rstepniewski.sockets.game.fleet.FleetLoader;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class GameBoardAIController {
    private static final Logger LOGGER = LogManager.getLogger(GameBoardAIController.class);
    private final GameBoard gameBoard;
    private final FleetLoader fleetLoader;
    Queue<String> possibleShots = new LinkedList<>();
    private Fleet fleet;

    public GameBoardAIController(GameBoard gameBoard, FleetLoader fleetLoader) {
        this.gameBoard = gameBoard;
        this.fleetLoader = fleetLoader;
    }

    public static void main(String[] args) {
        List<String> combinations = new ArrayList<>();
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 1; number <= 10; number++) {
                combinations.add(letter + Integer.toString(number));
            }
        }
        Collections.shuffle(combinations);

        Queue<String> shuffledCombinations = new LinkedList<>(combinations);
        String element = shuffledCombinations.poll();

        System.out.println(shuffledCombinations.size());
    }

    public void initialiseBord() {
        fleet = fleetLoader.loadRandomFleet();
        LOGGER.info("Server initialized it's gameboards succesfully");

        gameBoard.markShipPosition(fleet.getFleet());
        LOGGER.info("Server marked ships positions succesfully");

        gameBoard.printBoards();
        LOGGER.info("Boards printed succesfully");

        possibleShots = initialisePossibleShotsList();
        LOGGER.info("List with possible shots initialised");
    }

    public void markHitOnShotBoard(Point shot) {
        gameBoard.markHitOnShotBoard(shot, BoardCellStatus.HIT);
        UserInterface.printProperty("shoot.hit");
        gameBoard.printBoards();
    }

    public void markMissOnShotBoard(Point shot) {
        gameBoard.markHitOnShotBoard(shot, BoardCellStatus.MISS);
        UserInterface.printProperty("shoot.miss");
        gameBoard.printBoards();
    }

    public void markSinkingOnShotBoard(Point shot) {
        markHitOnShotBoard(shot);
        UserInterface.printProperty("shoot.sunk");
        gameBoard.printBoards();
    }

    public boolean isFleetAlive() {
        return !fleet.isFleetSunk();
    }

    public boolean isShipSinking(Point point) {
        Ship ship = fleet.findShip(point).get();
        return ship.isSinking();
    }

    public boolean isShotHit(Point shot) {
        Optional<Ship> ship = fleet.findShip(shot);
        return ship.isPresent();
    }

    public boolean isShotInBoundaries(Point shot) {
        return shot.getX() >= 0 && shot.getX() <= 9 && shot.getY() >= 0 && shot.getY() <= 9;
    }

    public boolean markHitOnShipBoard(Point shot) {
        Ship ship = fleet.findShip(shot).orElseThrow();

        Point hitPoint = ship.getPosition().stream()
                .filter(position -> position.equals(shot))
                .findFirst()
                .orElseThrow();

        hitPoint.setPointSinking(true);

        int index = ship.getPosition().indexOf(hitPoint);
        ship.getPosition().set(index, hitPoint);
        ship.setSinking(ship.isSinking());
        int shipIndex = fleet.getFleet().indexOf(ship);
        fleet.getFleet().set(shipIndex, ship);

        return ship.isSinking();
    }

    public void markHitOnFleetBoard(Point receivedShot, BoardCellStatus boardCellStatus) {
        gameBoard.markHitOnFleetBoard(receivedShot, boardCellStatus);
    }

    public Point getNewRandomShot() {
        return new Point(possibleShots.poll());
    }

    public Queue<String> initialisePossibleShotsList() {
        List<String> combinations = new ArrayList<>();
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 1; number <= 10; number++) {
                combinations.add(letter + Integer.toString(number));
            }
        }
        Collections.shuffle(combinations);

        Queue<String> shuffledCombinations = new LinkedList<>(combinations);

        return shuffledCombinations;
    }

    public void reportReceivedShot(Point receivedShot) {
        UserInterface.printText("The enemy shoot at " + receivedShot.toString()
                + ", X:" + receivedShot.getX()
                + " Y:" + receivedShot.getY());
    }

    public void printBoardsHistory(int sleeptime) {
        try {
            gameBoard.printBoardsHistory(sleeptime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> getBoardsHistory() {
        Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> boardsHistory = new HashMap<>();
        boardsHistory.put(0, gameBoard.getBoardShipsHistory());
        boardsHistory.put(1, gameBoard.getBoardShotsHistory());
        return boardsHistory;
    }

    public String convertBoardsHistoryToJson(Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> boardsHistory) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return objectMapper.writeValueAsString(boardsHistory);
    }

    public List<ShipDto> getFleetAsShipDtoList() {
        return fleet.getFleet().stream()
                .map(this::mapShipToShipDto)
                .collect(Collectors.toList());
    }

    private ShipDto mapShipToShipDto(Ship ship) {
        List<PointDto> pointDto = ship.getPosition().stream()
                .map(point -> new PointDto(point.getX(), point.getY(), point.isPointSinking()))
                .collect(Collectors.toList());

        return new ShipDto(pointDto, ship.isSinking());
    }
}