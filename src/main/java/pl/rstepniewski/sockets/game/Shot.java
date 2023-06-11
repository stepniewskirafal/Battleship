package pl.rstepniewski.sockets.game;

/**
 * Created by rafal on 11.06.2023
 *
 * @author : rafal
 * @date : 11.06.2023
 * @project : Battleship
 */
public class Shot {

    private final char row;
    private final int column;

    private Shot(char row, int column) {
        this.row = Character.toUpperCase(row);
        this.column = column;
    }
}
