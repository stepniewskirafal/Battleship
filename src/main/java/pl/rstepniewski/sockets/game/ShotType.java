package pl.rstepniewski.sockets.game;

public enum ShotType {
    HIT("HIT"),
    MISS("MISS"),
    SINKING("SINKING");

    final String shotType;


    ShotType(String shotType) {
        this.shotType = shotType;
    }

    public String getShotType() {
        return shotType;
    }
}
