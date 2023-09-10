package pl.rstepniewski.sockets.jsonCommunication.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.ShotType;
import pl.rstepniewski.sockets.jsonCommunication.RequestType;

import java.util.List;

public class Request extends Message{
    @JsonCreator
    public Request(
            @JsonProperty("type") String type,
            @JsonProperty("body") Object body
    ) {
        super(type, body);
    }

    public static Request gameInvitation() {
        return new Request(RequestType.GAME_INVITATION.name(), null);
    }

    public static Request shot(ShotDto shot) {
        return new Request(RequestType.SHOT.name(), shot);
    }

    public static Request shotRequest() {
        return new Request(RequestType.SHOT_REQUEST.name(), null);
    }

    public static Request shotResultHit() {
        return new Request(RequestType.RESULT.name(), ShotType.HIT.name());
    }

    public static Request shotResultMiss() {
        return new Request(RequestType.RESULT.name(), ShotType.MISS.name());
    }

    public static Request shotResultSinking() {
        return new Request(RequestType.RESULT.name(), ShotType.SINKING.name());
    }

    public static Request shipsArrangement(List<Ship> shipPosition) {
        return new Request(RequestType.BOARD.name(), shipPosition);
    }
}
