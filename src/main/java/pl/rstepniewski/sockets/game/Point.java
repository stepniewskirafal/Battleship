package pl.rstepniewski.sockets.game;

import java.util.Objects;

/**
 * Created by rafal on 24.06.2023
 *
 * @author : rafal
 * @date : 24.06.2023
 * @project : Battleship
 */
public class Point {
    private int x;
    private int y;
    private boolean isPointSinking;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.isPointSinking = false;
    }

    public Point(String positionOnBoard) {
        this(Integer.parseInt(positionOnBoard.substring(1)) - 1, positionOnBoard.toUpperCase().charAt(0) - 'A');
        this.isPointSinking = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPointSinking() {
        return isPointSinking;
    }

    public void setPointSinking(boolean pointSinking) {
        isPointSinking = pointSinking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && isPointSinking == point.isPointSinking;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isPointSinking);
    }
}
