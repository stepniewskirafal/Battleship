package pl.rstepniewski.sockets.jsonCommunication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.game.Shot;

public record Request(
        @JsonProperty("type") String type,
        @JsonProperty("body") Object body
) {
    @JsonCreator
    public Request {
    }

    public static Request gameInvitation() {
        return new Request(RequestType.GAME_INVITATION.name(), null);
    }

    public static Request shotClient(Shot shot) {
        return new Request(RequestType.SHOT.name(), shot);
    }

    public static Request shotRequest(Shot shot) {
        return new Request(RequestType.SHOT_REQUEST.name(), null);
    }
}
