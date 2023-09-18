package pl.rstepniewski.sockets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.game.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafal on 16.06.2023
 *
 * @author : rafal
 * @date : 16.06.2023
 * @project : Battleship
 */
public class ShipDto {
    private List<ShotDto> position;

    @JsonCreator
    public ShipDto(@JsonProperty("pozycja") List<ShotDto> position) {
        this.position = position;
    }

    @JsonProperty("position")
    public List<ShotDto> getPosition() {
        return position;
    }

}