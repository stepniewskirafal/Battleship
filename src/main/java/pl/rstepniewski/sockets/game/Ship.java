package pl.rstepniewski.sockets.game;

import java.util.List;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public record Ship(List<Point> pozycja, int dlugosc) {
    public Ship {
        dlugosc = pozycja.size();
    }
}