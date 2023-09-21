package pl.rstepniewski.sockets.jsonCommunication;

public enum MessageType {
    BOARD,
    BOARD_HISTORY,
    GAME_INVITATION,
    RESULT,
    SHOT,
    SHOT_REQUEST,
    UNKNOWN;

    public static MessageType getMessageTypeFromString(String value) {
        switch (value) {
            case "BOARD":
                return MessageType.BOARD;
            case "BOARD_HISTORY":
                return MessageType.BOARD_HISTORY;
            case "GAME_INVITATION":
                return MessageType.GAME_INVITATION;
            case "RESULT":
                return MessageType.RESULT;
            case "SHOT":
                return MessageType.SHOT;
            case "SHOT_REQUEST":
                return MessageType.SHOT_REQUEST;
            default:
                return MessageType.UNKNOWN;
        }
    }

}
