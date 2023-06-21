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
    private List<Point> position;
    private int length;

    public Ship(List<Point> pozycja) {
        this.position = pozycja;
        this.length = pozycja.size();
    }

    // Gettery dla pól pozycja i dlugosc

    public List<Point> getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public boolean isNeighbour(List<Point> newShipPosition){
        for(int i=0; i<newShipPosition.size(); i++){
            for(int j=0; j<position.size(); j++){
                if( position.get(j).x() == newShipPosition.get(j).x() && position.get(j).y() == newShipPosition.get(j).y() -1 ||
                    position.get(j).x() == newShipPosition.get(j).x() && position.get(j).y() == newShipPosition.get(j).y() +1 ||

                    position.get(j).y() == newShipPosition.get(j).y() && position.get(j).x() == newShipPosition.get(j).x() -1 ||
                    position.get(j).y() == newShipPosition.get(j).y() && position.get(j).x() == newShipPosition.get(j).x() +1 ){
                    return true;
                }
            }
        }
        return false;
    }
}