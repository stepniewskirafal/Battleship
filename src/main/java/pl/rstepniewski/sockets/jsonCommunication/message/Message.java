package pl.rstepniewski.sockets.jsonCommunication.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rafal on 10.07.2023
 *
 * @author : rafal
 * @date : 10.07.2023
 * @project : Battleship
 */
public abstract class Message {
    private String type;
    private Object body;

    @JsonCreator
    public Message(
            @JsonProperty("type") String type,
            @JsonProperty("body") Object body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}