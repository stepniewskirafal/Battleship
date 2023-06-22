package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
import java.util.List;
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
public class Board {
    private List<List<BoardCellStatus>> board = new ArrayList<>();

    public Board(){
        markEmptyPosition();
    }

    private void markEmptyPosition() {
        board = IntStream.range(0, 10)
                .mapToObj(i -> IntStream.range(0, 10)
                        .mapToObj(j -> BoardCellStatus.EMPTY)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void printBoard() {
        System.out.println("   A B C D E F G H I J");
        AtomicInteger rowNumber = new AtomicInteger(1);
        List<String> output = board.stream()
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

    public void markShipPosition(List<Ship> shipPosition) {
        shipPosition.forEach(ship -> {
            ship.getPosition()
                    .forEach(point -> board.get((int) point.x()).set((int) point.y(), BoardCellStatus.SHIP));
        });
    }

}
