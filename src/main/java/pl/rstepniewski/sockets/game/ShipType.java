package pl.rstepniewski.sockets.game;

public enum ShipType {
    ONE_MAST_SHIP_COUNT(4)
    ,TWO_MAST_SHIP_COUNT(3)
    ,THREE_MAST_SHIP_COUNT(2)
    ,FOUR_MAST_SHIP_COUNT(1)
    ,ONE_MAST_SHIP_LENGTH(1)
    ,TWO_MAST_SHIP_LENGTH(2)
    ,THREE_MAST_SHIP_LENGTH(3)
    ,FOUR_MAST_SHIP_LENGTH(4);

    private final int number;

    ShipType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
