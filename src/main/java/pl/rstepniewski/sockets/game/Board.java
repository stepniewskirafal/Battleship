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
    private static List<List<BoardCellStatus>> board = new ArrayList<>();

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




    static void markShipPosition(ShipPosition shipPosition) {

        shipPosition.ships()
                .stream()
                .map( x -> x.pozycja() )
                .forEach(System.out::println);
/*                .map( x-> x. )
                .forEach( {
                        int
                        x-> board.get((int) x. x()  ).set((int) x.getY(), BoardCellStatus.SHIP)
                }  );


        x-> board.get((int) x.get().x()).set((int) x.getY(), BoardCellStatus.SHIP)*/
    }

    public static ShipPosition convertShipsPosition(String[] shipCoordinates) {
        List<Point> pozycja = new ArrayList<>();
        List<Ship>  ships = new ArrayList<>();
        for (String shipCoordinate : shipCoordinates) {
            pozycja.clear();
            Point pointStart = new Point(shipCoordinate.substring(0,1));
            Point pointEnd = new Point(shipCoordinate.substring(2,3));

            for (int x = pointStart.x(); x <= pointEnd.x(); x++) {
                for (int y = pointStart.y(); y <= pointEnd.y(); y++) {
                    pozycja.add(new Point(x, y));
                }
            }
            ships.add(new Ship(pozycja, pozycja.size()));
        }

        return new ShipPosition(ships);
    }

}
