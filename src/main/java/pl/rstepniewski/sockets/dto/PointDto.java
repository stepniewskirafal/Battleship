package pl.rstepniewski.sockets.dto;

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
    private final int x;
    private final int y;
    private final boolean pointSinking;

    @JsonCreator
    public PointDto(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("pointSinking") boolean pointSinking) {
        this.x = x;
        this.y = y;
        this.pointSinking = pointSinking;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean pointSinking() {
        return pointSinking;
    }

}
