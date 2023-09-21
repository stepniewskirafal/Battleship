package pl.rstepniewski.sockets.game.board;

public enum BoardCellStatus { EMPTY, SHIP, HIT, MISS;

    public static BoardCellStatus valueFromString(String statusString) {
        return valueOf(statusString);
    }
}
