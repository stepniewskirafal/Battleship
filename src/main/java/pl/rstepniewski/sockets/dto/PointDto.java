package pl.rstepniewski.sockets.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rafal on 24.06.2023
 *
 * @author : rafal
 * @date : 24.06.2023
 * @project : Battleship
 */
public class PointDto {
    private int x;
    private int y;
    private boolean isPointSinking;

    public PointDto(int x, int y) {
        this.x = x;
        this.y = y;
        this.isPointSinking = false;
    }

    public PointDto(String positionOnBoard) {
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
        PointDto point = (PointDto) o;
        return x == point.x && y == point.y && isPointSinking == point.isPointSinking;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isPointSinking);
    }

    @Override
    public String toString() {
        char letter = (char) ('A' + y);
        int rowNumber = x + 1;

        return letter + String.valueOf(rowNumber);
    }
}
