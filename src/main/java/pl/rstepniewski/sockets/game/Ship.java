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

    public Ship(List<Point> pozycja) {
        this.position = pozycja;
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
        for(Point newPoint: newShipPosition){
            for(Point currentPoint: position){
                if(( currentPoint.x() == newPoint.x() && currentPoint.y() == newPoint.y() -1 ) ||
                        ( currentPoint.x() == newPoint.x() && currentPoint.y() == newPoint.y() +1 ) ||
                        ( currentPoint.y() == newPoint.y() && currentPoint.x() == newPoint.x() -1 ) ||
                        ( currentPoint.y() == newPoint.y() && currentPoint.x() == newPoint.x() +1 )){
                    return true;
                }
            }
        }
        return false;
    }
}