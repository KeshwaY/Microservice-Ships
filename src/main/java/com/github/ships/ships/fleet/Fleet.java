package com.github.ships.ships.fleet;

import java.util.ArrayList;
import java.util.List;

public class Fleet {

    private final List<Ship> ships;
    private final List<Integer> sizesOfShipsToBePlaced;

    public Fleet(List<Ship> ships, int boardWidth, int boardHeight) {
        this.ships = ships;
        this.sizesOfShipsToBePlaced = generateSizesOfShipsToBePlaced();
    }

    /* 1x   4-mast
     * 2x   3-mast
     * 3x   2-mast
     * 4x   1-mast */
    // TODO: Make customizable/read from a file
    private List<Integer> generateSizesOfShipsToBePlaced() {
        ArrayList<Integer> sizesOfShipsToBePlaced = new ArrayList<>();
        sizesOfShipsToBePlaced.addAll(List.of(4));
        sizesOfShipsToBePlaced.addAll(List.of(3, 3));
        sizesOfShipsToBePlaced.addAll(List.of(2, 2, 2));
        sizesOfShipsToBePlaced.addAll(List.of(1, 1, 1, 1));
        return sizesOfShipsToBePlaced;
    }


}
