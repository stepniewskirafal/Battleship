package pl.rstepniewski.sockets.jsonCommunication.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.ShotType;
import pl.rstepniewski.sockets.jsonCommunication.MessageType;

public class Response extends Message{
    private int status;
    private String message;

    @JsonCreator
    public Response(
            @JsonProperty("type") String type,
            @JsonProperty("status") int status,
            @JsonProperty("message") String message,
            @JsonProperty("body") Object body
    ) {
        super(type, body);
        this.status = status;
        this.message = message;
    }
    public static Response gameInvitationPositive() {
        return new Response(MessageType.GAME_INVITATION.name(), 0, null, null);
    }

    public static Response gameInvitationNegative() {
        return new Response(MessageType.GAME_INVITATION.name(), 1, "Server is playing the other game.", null);
    }

    public static Response shotResultHit() {
        return new Response(MessageType.SHOT.name(), 0, null, ShotType.HIT.name());
    }

    public static Response shotResultMiss() {
        return new Response(MessageType.SHOT.name(), 0, null, ShotType.MISS.name());
    }

    public static Response shotResultShipSinking() {
        return new Response(MessageType.SHOT.name(), 0, null, ShotType.SINKING.name());
    }

    public static Response shotResultFleetSinking() {
        return new Response(MessageType.SHOT.name(), 0, null, null);
    }

    public static Response shotResultUnknown(ShotType shotType) {
        return new Response(MessageType.SHOT.name(), 0, "The shot is not within the boundaries of the board.", null);
    }

    public static Response shot(ShotDto shot) {
        return new Response(MessageType.SHOT_REQUEST.name(), 0, null, shot);
    }

    public static Response acceptShotResult() {
        return new Response(MessageType.RESULT.name(), 0, null, null);
    }

    public static Response serverStatus(int errorNumber) {
        return new Response(MessageType.UNKNOWN.name(), errorNumber, null, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
