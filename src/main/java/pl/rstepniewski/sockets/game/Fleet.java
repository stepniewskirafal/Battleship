package pl.rstepniewski.sockets.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    public boolean isFleetAilive(){
        return fleet.stream().anyMatch(Ship::isSinking);
    }
    
    public Ship findShip(Point point){
        return fleet.stream()
                .filter(ship -> ship.getPosition().contains(point))
                .findFirst()
                .orElseThrow();
    }

    List<Ship> getFleet() {
        return fleet;
    }
}
