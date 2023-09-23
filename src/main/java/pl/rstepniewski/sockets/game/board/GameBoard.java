package pl.rstepniewski.sockets.game.board;

import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.UserInterface;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by rafal on 13.06.2023
 *
 * @author : rafal
 * @date : 13.06.2023
 * @project : Battleship
 */
public class GameBoard implements UserInterface {
    private final GameBoardHistory gameBoardHistory;
    private List<List<BoardCellStatus>> boardShips = new ArrayList<>();
    private List<List<BoardCellStatus>> boardShots = new ArrayList<>();

    private Map<Integer, List<List<BoardCellStatus>>> boardShipsHistory;
    private Map<Integer, List<List<BoardCellStatus>>> boardShotsHistory;

    public GameBoard(){
        gameBoardHistory = new GameBoardHistory();
        boardShipsHistory = gameBoardHistory.getBoardShipsHistory();
        boardShotsHistory = gameBoardHistory.getBoardShotsHistory();
        markEmptyPosition();
    }

    public Map<Integer, List<List<BoardCellStatus>>> getBoardShipsHistory() {
        return gameBoardHistory.getBoardShipsHistory();
    }

    public Map<Integer, List<List<BoardCellStatus>>> getBoardShotsHistory() {
        return gameBoardHistory.getBoardShotsHistory();
    }

    public boolean isShotNotAllowed(Point point){
        BoardCellStatus boardCellStatus = boardShips.get(point.getX()).get(point.getY());
        if(boardCellStatus.equals(BoardCellStatus.HIT) || boardCellStatus.equals(BoardCellStatus.MISS)){
            return true;
        }
        return false;
    }

    public void markEmptyPosition() {
        boardShips = IntStream.range(0, 10)
                .mapToObj(i -> IntStream.range(0, 10)
                        .mapToObj(j -> BoardCellStatus.EMPTY)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        boardShots = IntStream.range(0, 10)
                .mapToObj(i -> IntStream.range(0, 10)
                        .mapToObj(j -> BoardCellStatus.EMPTY)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void printShipBoard() {
        UserInterface.printText("   A B C D E F G H I J");
        AtomicInteger rowNumber = new AtomicInteger(1);
        List<String> output = boardShips.stream()
                .map(rowList -> {
                    StringBuilder rowBegin = (rowNumber.get() < 10) ? new StringBuilder(rowNumber.getAndIncrement() + "  ") : new StringBuilder(rowNumber.getAndIncrement() + " ");
                    StringBuilder builder = new StringBuilder( rowBegin );
                    return builder.append(
                            rowList.stream()
                                    .map(cell -> {
                                        switch (cell) {
                                            case EMPTY:
                                                return "~ ";
                                            case SHIP:
                                                return "O ";
                                            case HIT:
                                                return "X ";
                                            case MISS:
                                                return "M ";
                                            default:
                                                return "";
                                        }
                                    })
                                    .collect(Collectors.joining())
                    ).toString();
                })
                .collect(Collectors.toList());
        output.forEach(System.out::println);
    }


    public void printBoardsHistory(int sleeptime, Map<Integer, List<List<BoardCellStatus>>> serverBoardShipsHistory, Map<Integer, List<List<BoardCellStatus>>> serverBoardShotsHistory) throws InterruptedException {
        Map<Integer, List<List<BoardCellStatus>>> boardShipsHistory = gameBoardHistory.getBoardShipsHistory();
        Map<Integer, List<List<BoardCellStatus>>> boardShotsHistory = gameBoardHistory.getBoardShotsHistory();

        int boardShipsHistorySize = boardShipsHistory.size();
        int boardShotsHistorySize = boardShotsHistory.size();

        int serverBoardShipsHistorySize = serverBoardShipsHistory.size();
        int serverBoardShotsHistorySize = serverBoardShotsHistory.size();

        int maxHistorySize = Math.max(boardShipsHistorySize, boardShotsHistorySize);

        for (int i = 1; i <= maxHistorySize; i++) {
            List<List<BoardCellStatus>> boardShipsToDraw = boardShipsHistory.get(i <= boardShipsHistorySize ? i : boardShipsHistorySize );
            List<List<BoardCellStatus>> boardShotsToDraw = boardShotsHistory.get(i <= boardShotsHistorySize ? i : boardShotsHistorySize );
            List<String> outputToDraw;
            UserInterface.printText("   ROUND : "+i);
            UserInterface.printText("   Your fleet             Your shots                                                 legend:");
            UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J                                        O: Ship    X: Hit    M: Missed Shot");
            outputToDraw  = getBoardOutput(boardShipsToDraw, boardShotsToDraw);
            outputToDraw.forEach(System.out::println);

            List<List<BoardCellStatus>> serverBoardShipsToDraw = serverBoardShipsHistory.get(i <= serverBoardShipsHistorySize ? i : serverBoardShipsHistorySize );
            List<List<BoardCellStatus>> serverBoardShotsToDraw = serverBoardShotsHistory.get(i <= serverBoardShotsHistorySize ? i : serverBoardShotsHistorySize );
            UserInterface.printText("   Opponent fleet         Opponent shots         ");
            UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J    ");
            outputToDraw = getBoardOutput(serverBoardShipsToDraw, serverBoardShotsToDraw);
            outputToDraw.forEach(System.out::println);
            UserInterface.printText("");
            UserInterface.printText("");
            UserInterface.printText("");
            Thread.sleep(sleeptime);
        }
    }

    public void printBoardsHistory(int sleeptime) throws InterruptedException {
        Map<Integer, List<List<BoardCellStatus>>> boardShipsHistory = gameBoardHistory.getBoardShipsHistory();
        Map<Integer, List<List<BoardCellStatus>>> boardShotsHistory = gameBoardHistory.getBoardShotsHistory();

        int boardShipsHistorySize = boardShipsHistory.size();
        int boardShotsHistorySize = boardShotsHistory.size();

        int maxHistorySize = Math.max(boardShipsHistorySize, boardShotsHistorySize);

        for (int i = 1; i <= maxHistorySize; i++) {
            List<List<BoardCellStatus>> boardShipsToDraw = boardShipsHistory.get(i <= boardShipsHistorySize ? i : boardShipsHistorySize );
            List<List<BoardCellStatus>> boardShotsToDraw = boardShotsHistory.get(i <= boardShotsHistorySize ? i : boardShotsHistorySize );

            UserInterface.printText("   ROUND : "+i);
            UserInterface.printText("   You're fleet           You're shots                                               legend:");
            UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J                                        O: Ship    X: Hit    M: Missed Shot");

            List<String> output = getBoardOutput(boardShipsToDraw, boardShotsToDraw);
            output.forEach(System.out::println);
            UserInterface.printText("");
            UserInterface.printText("");
            Thread.sleep(sleeptime);
        }
    }

    public void printBoards() {
        UserInterface.printText("   You're fleet           You're shots                                               legend:");
        UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J                                        O: Ship    X: Hit    M: Missed Shot");

        List<String> output = getBoardOutput(boardShips, boardShots);
        output.forEach(System.out::println);
        UserInterface.printText("");
        UserInterface.printText("");
    }

    private List<String> getBoardOutput(List<List<BoardCellStatus>> boardShipsToDraw, List<List<BoardCellStatus>> boardShotsToDraw) {
        AtomicInteger counter = new AtomicInteger(1);
        return IntStream.range(0, 10)
                .mapToObj(index -> {
                    StringBuilder rowNumber = (counter.get() < 10) ? new StringBuilder(counter.getAndIncrement() + "  ") : new StringBuilder(counter.getAndIncrement() + " ");
                    StringBuilder builder = new StringBuilder(rowNumber);
                    List<BoardCellStatus> rowList1 = boardShipsToDraw.get(index);
                    List<BoardCellStatus> rowList2 = boardShotsToDraw.get(index);

                    StringBuilder rowShips = new StringBuilder();
                    StringBuilder rowShots = new StringBuilder();
                    for (int i = 0; i < rowList1.size(); i++) {
                        BoardCellStatus cell1 = rowList1.get(i);
                        BoardCellStatus cell2 = rowList2.get(i);

                        switch (cell1) {
                            case EMPTY:
                                rowShips.append("~ ");
                                break;
                            case SHIP:
                                rowShips.append("O ");
                                break;
                            case HIT:
                                rowShips.append("X ");
                                break;
                            case MISS:
                                rowShips.append("M ");
                                break;
                            default:
                                rowShips.append("");
                                break;
                        }

                        switch (cell2) {
                            case EMPTY:
                                rowShots.append("~ ");
                                break;
                            case SHIP:
                                rowShots.append("O ");
                                break;
                            case HIT:
                                rowShots.append("X ");
                                break;
                            case MISS:
                                rowShots.append("M ");
                                break;
                            default:
                                rowShots.append("");
                                break;
                        }
                    }
                    return builder.append(rowShips)
                            .append("   ")
                            .append(rowShots)
                            .append(rowNumber)
                            .toString();

                })
                .collect(Collectors.toList());
    }

    public void markShipPosition(List<Ship> shipPosition) {
        shipPosition.forEach(ship -> {
            ship.getPosition()
                    .forEach(point -> boardShips.get(point.getX()).set(point.getY(), BoardCellStatus.SHIP));
        });
    }

    public void markHitOnShotBoard(Point shot, BoardCellStatus boardCellStatus) {
        boardShots.get(shot.getX()).set(shot.getY(), boardCellStatus);
        gameBoardHistory.addToBoardHistory(boardShotsHistory, boardShots);
    }

    public void markHitOnFleetBoard(Point shot, BoardCellStatus boardCellStatus) {
        boardShips.get(shot.getX()).set(shot.getY(), boardCellStatus);
        gameBoardHistory.addToBoardHistory(boardShipsHistory, boardShips);
    }

}
