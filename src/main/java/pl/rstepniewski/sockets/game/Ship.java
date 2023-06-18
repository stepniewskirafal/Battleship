package pl.rstepniewski.sockets.game;

import java.util.List;
/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class Ship {
    private List<Point> pozycja;
    private int dlugosc;

    public Ship(List<Point> pozycja) {
        this.pozycja = pozycja;
        this.dlugosc = pozycja.size();
    }

    // Gettery dla pól pozycja i dlugosc

    public List<Point> getPozycja() {
        return pozycja;
    }

    public int getDlugosc() {
        return dlugosc;
    }
}