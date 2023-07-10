package pl.rstepniewski.sockets.jsonCommunication;

public enum MessageType {
    GAME_INVITATION,
    RESULT,
    SHOT,
    SHOT_REQUEST,
    UNKNOWN;

    public static MessageType getMessageTypeFromString(String value) {
        switch (value) {
            case "GAME_INVITATION":
                return MessageType.GAME_INVITATION;
            case "RESULT":
                return MessageType.RESULT;
            case "SHOT":
                return MessageType.SHOT;
            case "SHOT_REQUEST":
                return MessageType.SHOT_REQUEST;
            case "UNKNOWN":
                return MessageType.UNKNOWN;
            default:
                return MessageType.UNKNOWN;
        }
    }
}