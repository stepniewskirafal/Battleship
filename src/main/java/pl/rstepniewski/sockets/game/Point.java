package pl.rstepniewski.sockets.game;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public record Point ( int x,
         int y){

    public Point(String positionOnBoard){
        this(Integer.parseInt(positionOnBoard.substring(1)) - 1, positionOnBoard.toUpperCase().charAt(0) - 'A');
    }

}
