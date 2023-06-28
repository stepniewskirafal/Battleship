package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
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
    private List<Ship> fleet = new ArrayList<>();

    public Fleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public boolean isFleetSunk(){
        return fleet.stream().allMatch(Ship::isSinking);
    }
    
    public Optional<Ship> findShip(Point point){
        return fleet.stream()
                .filter(ship -> ship.getPosition().contains(point)
                )
                .findAny();
    }

    List<Ship> getFleet() {
        return fleet;
    }
}
