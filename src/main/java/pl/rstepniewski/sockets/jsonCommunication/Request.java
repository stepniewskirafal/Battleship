package pl.rstepniewski.sockets.jsonCommunication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.dto.ShotDto;
import pl.rstepniewski.sockets.game.Ship;
import pl.rstepniewski.sockets.game.ShotType;

import java.util.List;

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

    public static Request shot(ShotDto shot) {
        return new Request(RequestType.SHOT.name(), shot);
    }

    public static Request shotRequest() {
        return new Request(RequestType.SHOT_REQUEST.name(), null);
    }

    public static Request shotResultHit(ShotType shotType) {
        return new Request(RequestType.RESULT.name(), ShotType.HIT.name());
    }

    public static Request shotResultMiss(ShotType shotType) {
        return new Request(RequestType.RESULT.name(), ShotType.MISS.name());
    }

    public static Request shotResultSinking(ShotType shotType) {
        return new Request(RequestType.RESULT.name(), ShotType.SINKING.name());
    }

    public static Request shipsArrangement(List<Ship> shipPosition) {
        return new Request(RequestType.BOARD.name(), shipPosition);
    }
}
