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
    private final List<Point> position;
    private boolean isShipSinking;
    private final int length;

    public Ship(List<Point> position, int length) {
        this.position = position;
        this.length = length;
        this.isShipSinking = false;
    }

    public List<Point> getPosition() {
        return position;
    }

    public static int getLength(List<Point> newShipPoints) {
        Point p1 = newShipPoints.get(0);
        Point p2 = newShipPoints.get(1);

        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();

        return (int) (Math.sqrt(dx * dx + dy * dy) + 1);
    }

    public boolean isNeighbour(List<Point> newShipPosition) {
        for (int i = 0; i < newShipPosition.size(); i++) {
            for (int j = 0; j < position.size(); j++) {
                if (position.get(j).getX() == newShipPosition.get(i).getX() && position.get(j).getY() == newShipPosition.get(i).getY() ||
                        position.get(j).getX() == newShipPosition.get(i).getX() && position.get(j).getY() == newShipPosition.get(i).getY() - 1 ||
                        position.get(j).getX() == newShipPosition.get(i).getX() && position.get(j).getY() == newShipPosition.get(i).getY() + 1 ||

                        position.get(j).getY() == newShipPosition.get(i).getY() && position.get(j).getX() == newShipPosition.get(i).getX() ||
                        position.get(j).getY() == newShipPosition.get(i).getY() && position.get(j).getX() == newShipPosition.get(i).getX() - 1 ||
                        position.get(j).getY() == newShipPosition.get(i).getY() && position.get(j).getX() == newShipPosition.get(i).getX() + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSinking() {
        return position.stream().allMatch(Point::isPointSinking);
    }

    public void setSinking(boolean sinking) {
        isShipSinking = sinking;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "position=" + position +
                ", isShipSinking=" + isShipSinking +
                ", length=" + length +
                '}';
    }
}