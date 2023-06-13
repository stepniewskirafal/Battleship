package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by rafal on 13.06.2023
 *
 * @author : rafal
 * @date : 13.06.2023
 * @project : Battleship
 */
public class Board {

    private final ShipPosition shipPosition;
    private static List<List<BoardCellStatus>> board = new ArrayList<>();


    Board(ShipPosition shipPosition) {
        this.shipPosition = shipPosition;
        initiateBoard();
    }

    public void initiateBoard(){ // przpisz z main
        markEmptyPosition();
        markShipPosition();
    }


    public static void printBoard(List<List<BoardCellStatus>> board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        final char[] letter = {'A'};
        List<String> output = board.stream()
                .map(rowList -> {
                    StringBuilder builder = new StringBuilder(letter[0] + " ");
                    letter[0]++;
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


    private static void markEmptyPosition() {
        board = IntStream.range(0, 10)
                .mapToObj(i -> IntStream.range(0, 10)
                        .mapToObj(j -> BoardCellStatus.EMPTY)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static void markShipPosition() {
    }

/*    public static void main(String[] args) {
        markEmptyPosition();
        printBoard(board);
    }*/
}
