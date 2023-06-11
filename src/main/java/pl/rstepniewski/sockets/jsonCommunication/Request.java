package pl.rstepniewski.sockets.jsonCommunication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.game.Shot;

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

    public static Request gameInvitation(){ return new Request(RequestType.GAME_INVITATION.name(), null);}
    public static Request shotClient(Shot shot){ return new Request(RequestType.SHOT.name(), shot);}
    public static Request shotRequest(Shot shot){ return new Request(RequestType.SHOT_REQUEST.name(), null);}





}