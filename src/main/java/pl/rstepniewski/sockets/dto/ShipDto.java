package pl.rstepniewski.sockets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class ShipDto {
    private final List<PointDto> position;
    private final boolean isShipSinking;

    @JsonCreator
    public ShipDto(@JsonProperty("position") List<PointDto> position, @JsonProperty("isShipSinking") boolean isShipSinking) {
        this.position = position;
        this.isShipSinking = isShipSinking;
    }

    @JsonProperty("position")
    public List<PointDto> getPosition() {
        return position;
    }

    @JsonProperty("isShipSinking")
    public boolean isShipSinking() {
        return isShipSinking;
    }
}