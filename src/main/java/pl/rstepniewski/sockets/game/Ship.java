package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
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
    private boolean isSinking;
    private int length;

    public Ship(List<Point> pozycja, int length) {
        this.position = pozycja;
        this.isSinking = false;
    }

    public List<Point> getPosition() {
        return position;
    }

    public static int getLength(ArrayList<Point> newShipPoints) {
        Point p1 = newShipPoints.get(0);
        Point p2 = newShipPoints.get(1);

        double dx = p2.x() - p1.x();
        double dy = p2.y() - p1.y();

        return (int) (Math.sqrt(dx * dx + dy * dy) + 1);
    }

    public boolean isNeighbour(List<Point> newShipPosition){
        for(int i=0; i<newShipPosition.size(); i++){
            for(int j=0; j<position.size(); j++){
                if(     position.get(j).x() == newShipPosition.get(i).x() && position.get(j).y() == newShipPosition.get(i).y()    ||
                        position.get(j).x() == newShipPosition.get(i).x() && position.get(j).y() == newShipPosition.get(i).y() -1 ||
                        position.get(j).x() == newShipPosition.get(i).x() && position.get(j).y() == newShipPosition.get(i).y() +1 ||

                        position.get(j).y() == newShipPosition.get(i).y() && position.get(j).x() == newShipPosition.get(i).x()    ||
                        position.get(j).y() == newShipPosition.get(i).y() && position.get(j).x() == newShipPosition.get(i).x() -1 ||
                        position.get(j).y() == newShipPosition.get(i).y() && position.get(j).x() == newShipPosition.get(i).x() +1 ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSinking() {
        return isSinking;
    }

    public void setSinking(boolean sinking) {
        isSinking = sinking;
    }

    public int getLength() {
        return length;
    }
}