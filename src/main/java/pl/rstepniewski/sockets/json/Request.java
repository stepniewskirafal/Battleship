package pl.rstepniewski.sockets.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rafal on 11.06.2023
 *
 * @author : rafal
 * @date : 11.06.2023
 * @project : Battleship
 */
public class Request {
    private String type;
    private Object body;

    @JsonCreator
    public Request(@JsonProperty("type") String type, @JsonProperty("body") Object body) {
        this.type = type;
        this.body = body;
    }
}