package pl.rstepniewski.sockets.jsonCommunication;

public enum MessageType {
    BOARD,
    GAME_INVITATION,
    RESULT,
    SHOT,
    SHOT_REQUEST,
    SHOT_STATUS,
    UNKNOWN;

    public static MessageType getMessageTypeFromString(String value) {
        switch (value) {
            case "BOARD":
                return MessageType.BOARD;
            case "GAME_INVITATION":
                return MessageType.GAME_INVITATION;
            case "RESULT":
                return MessageType.RESULT;
            case "SHOT":
                return MessageType.SHOT;
            case "SHOT_REQUEST":
                return MessageType.SHOT_REQUEST;
            case "SHOT_STATUS":
                return MessageType.SHOT_STATUS;
            default:
                return MessageType.UNKNOWN;
        }
    }

}
