package pl.rstepniewski.sockets.game.board;

import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.UserInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<List<BoardCellStatus>> boardShips = new ArrayList<>();
    private List<List<BoardCellStatus>> boardShots = new ArrayList<>();

    private Map<Byte, List<List<BoardCellStatus>>> boardShipsHistory = new HashMap<>();
    private Map<Byte, List<List<BoardCellStatus>>> boardShotsHistory = new HashMap<>();

    public void addToBoardShipsHistory(List<List<BoardCellStatus>> boardShips){
        boardShipsHistory.put((byte) (boardShipsHistory.size() + 1), boardShips);
    }
    public void addToBoardShotsHistory(List<List<BoardCellStatus>> boardShots){
        boardShipsHistory.put((byte) (boardShipsHistory.size() + 1), boardShips);
    }

    public GameBoard(){
        markEmptyPosition();
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
        addToBoardShipsHistory(boardShips);

        boardShots = IntStream.range(0, 10)
                .mapToObj(i -> IntStream.range(0, 10)
                        .mapToObj(j -> BoardCellStatus.EMPTY)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        addToBoardShotsHistory(boardShots);
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


    public void printBoardsHistory() throws InterruptedException {
        for(int i=0; i<=boardShips.size(); i++){
            UserInterface.printText("   You're fleet           You're shots                                               legend:");
            UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J                                        O: Ship    X: Hit    M: Missed Shot");
            AtomicInteger counter = new AtomicInteger(1);
            List<String> output = getBoardOutput(counter, boardShipsHistory.get(i), boardShotsHistory.get(i));
            output.forEach(System.out::println);
            UserInterface.printText("");
            UserInterface.printText("");
            Thread.sleep(3000);
        }
    }

    public void printBoards() {
        UserInterface.printText("   You're fleet           You're shots                                               legend:");
        UserInterface.printText("   A B C D E F G H I J    A B C D E F G H I J                                        O: Ship    X: Hit    M: Missed Shot");
        AtomicInteger counter = new AtomicInteger(1);
        List<String> output = getBoardOutput(counter, boardShips, boardShots);
        output.forEach(System.out::println);
        UserInterface.printText("");
        UserInterface.printText("");
    }

    private List<String> getBoardOutput(AtomicInteger counter, List<List<BoardCellStatus>> boardShips, List<List<BoardCellStatus>> boardShots) {
        return IntStream.range(0, this.boardShips.size())
                .mapToObj(index -> {
                    StringBuilder rowNumber = (counter.get() < 10) ? new StringBuilder(counter.getAndIncrement() + "  ") : new StringBuilder(counter.getAndIncrement() + " ");
                    StringBuilder builder = new StringBuilder(rowNumber);
                    List<BoardCellStatus> rowList1 = this.boardShips.get(index);
                    List<BoardCellStatus> rowList2 = this.boardShots.get(index);

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
        addToBoardShipsHistory(boardShips);
        addToBoardShotsHistory(boardShots);
    }

    public void markHitOnShotBoard(Point shot, BoardCellStatus boardCellStatus) {
        boardShots.get(shot.getX()).set(shot.getY(), boardCellStatus);
        addToBoardShipsHistory(boardShips); 
        addToBoardShotsHistory(boardShots);
    }

    public void markHitOnFleetBoard(Point shot, BoardCellStatus boardCellStatus) {
        boardShips.get(shot.getX()).set(shot.getY(), boardCellStatus);
        addToBoardShipsHistory(boardShips);
        addToBoardShotsHistory(boardShots);
    }

    public void markShotShooted(Point shot, BoardCellStatus boardCellStatus) {
        boardShips.get(shot.getX()).set(shot.getY(), boardCellStatus);
        addToBoardShipsHistory(boardShips);
        addToBoardShotsHistory(boardShots);
    }

}
