package pl.rstepniewski.sockets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShotDto {
    private final int row;
    private final int column;

    @JsonCreator
    public ShotDto(@JsonProperty("x") int row, @JsonProperty("y") int column) {
        this.row = row;
        this.column = column;
    }

    @JsonProperty("x")
    public int getRow() {
        return row;
    }

    @JsonProperty("y")
    public int getColumn() {
        return column;
    }
}
