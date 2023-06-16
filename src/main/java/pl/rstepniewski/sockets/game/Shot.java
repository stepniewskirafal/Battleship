package pl.rstepniewski.sockets.game;

/**
 * Created by rafal on 11.06.2023
 *
 * @author : rafal
 * @date : 11.06.2023
 * @project : Battleship
 */
public record Shot(char row, int column) {
    public Shot {
        row = Character.toUpperCase(row);
    }
}