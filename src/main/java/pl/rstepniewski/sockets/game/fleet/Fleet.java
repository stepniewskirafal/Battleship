package pl.rstepniewski.sockets.game.fleet;

import pl.rstepniewski.sockets.game.Point;
import pl.rstepniewski.sockets.game.Ship;

import java.util.List;
import java.util.Optional;

/**
 * Created by rafal on 24.06.2023
 *
 * @author : rafal
 * @date : 24.06.2023
 * @project : Battleship
 */
public class Fleet {
    private final List<Ship> fleet;

    public Fleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public Optional<Ship> findShip(Point point) {
        return fleet.stream()
                .filter(ship -> ship.getPosition().contains(point))
                .findAny();
    }

    public boolean isFleetSunk() {
        return fleet.stream().allMatch(Ship::isSinking);
    }
}
