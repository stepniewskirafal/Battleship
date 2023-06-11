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
public class Response {
    private String type;
    private int status;
    private String message;
    private Object body;

    @JsonCreator
    public Response(@JsonProperty("type") String type, @JsonProperty("status") int status,
                    @JsonProperty("message") String message, @JsonProperty("body") Object body) {
        this.type = type;
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getBody() {
        return body;
    }
}