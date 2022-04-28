package com.github.ships.ships.fleet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: redesign method to produce random fleet
public class FleetFactory {

    // key: player ID (currently only 1 and 2)
    private final Map<Integer, List<Ship>> shipsCollection;
    private final int playerId;
    private final int boardWidth;
    private final int boardHeight;

    private FleetFactory(int playerId, int boardWidth, int boardHeight) {
        this.playerId = playerId;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        shipsCollection = new HashMap<>();
        shipsCollection.put(1, initCollectionForPlayerOne());
        shipsCollection.put(2, initCollectionForPlayerTwo());
    }

    /* Temporary method. Be replaced by randomization
     * Ships to be placed:
     * 1x   4-mast
     * 2x   3-mast
     * 3x   2-mast
     * 4x   1-mast */
    public static List<Ship> produceShips(int playerId, int boardWidth, int boardHeight) {
        if (playerId < 1 || playerId > 2) {
            throw new IllegalArgumentException("Player ID can be only 1 or 2");
        }
        FleetFactory fleetFactory = new FleetFactory(playerId, boardWidth, boardHeight);
        return fleetFactory.produceShips();
    }

    private List<Ship> produceShips() {
        return shipsCollection.get(playerId);
    }

    private List<Ship> initCollectionForPlayerOne() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(List.of(22, 32, 42, 52), boardWidth, boardHeight));
        ships.add(new Ship(List.of(24, 25, 26), boardWidth, boardHeight));
        ships.add(new Ship(List.of(55, 56, 57), boardWidth, boardHeight));
        ships.add(new Ship(List.of(10, 20), boardWidth, boardHeight));
        ships.add(new Ship(List.of(84, 85), boardWidth, boardHeight));
        ships.add(new Ship(List.of(71, 81), boardWidth, boardHeight));
        ships.add(new Ship(List.of(1), boardWidth, boardHeight));
        ships.add(new Ship(List.of(4), boardWidth, boardHeight));
        ships.add(new Ship(List.of(39), boardWidth, boardHeight));
        ships.add(new Ship(List.of(89), boardWidth, boardHeight));
        return ships;
    }

    private List<Ship> initCollectionForPlayerTwo() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(List.of(67, 77, 87, 97), boardWidth, boardHeight));
        ships.add(new Ship(List.of(9, 19, 29), boardWidth, boardHeight));
        ships.add(new Ship(List.of(63, 64, 65), boardWidth, boardHeight));
        ships.add(new Ship(List.of(3, 13), boardWidth, boardHeight));
        ships.add(new Ship(List.of(89, 90), boardWidth, boardHeight));
        ships.add(new Ship(List.of(92, 93), boardWidth, boardHeight));
        ships.add(new Ship(List.of(1), boardWidth, boardHeight));
        ships.add(new Ship(List.of(35), boardWidth, boardHeight));
        ships.add(new Ship(List.of(41), boardWidth, boardHeight));
        ships.add(new Ship(List.of(59), boardWidth, boardHeight));
        return ships;
    }

}
