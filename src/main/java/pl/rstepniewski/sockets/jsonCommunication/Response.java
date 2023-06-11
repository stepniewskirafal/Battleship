package pl.rstepniewski.sockets.jsonCommunication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.game.Shot;
import pl.rstepniewski.sockets.game.ShotType;

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

    public static Response gameInvitationPositive() {return new Response(ResponseType.GAME_INVITATION.name(), 0,null,null);}
    public static Response gameInvitationNegative() {return new Response(ResponseType.GAME_INVITATION.name(), 1,"Server is playing the other game.",null);}
    public static Response shotStatusHit(ShotType shotType){ return new Response(RequestType.SHOT.name(), 0, null, ShotType.HIT.name());}
    public static Response shotStatusMiss(ShotType shotType){ return new Response(RequestType.SHOT.name(), 0, null, ShotType.MISS.name());}
    public static Response shotStatusSinking(ShotType shotType){ return new Response(RequestType.SHOT.name(), 0, null, ShotType.SINKING.name());}
    public static Response shotStatusUnknown(ShotType shotType){ return new Response(RequestType.SHOT.name(), 0, "The shot is not within the boundaries of the board.", null);}
    public static Response shotServer(Shot shot){ return new Response(RequestType.SHOT_REQUEST.name(), 0, null, shot);}





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