package com.github.ships.ships.fleet;

import java.util.Map;

public enum ShipType {
    DESTROYER(1),
    SUBMARINE(2),
    CRUISER(3),
    BATTLESHIP(4);

    private static Map<Integer, ShipType> shipTypeMap;

    static {
        shipTypeMap.put(1, DESTROYER);
        shipTypeMap.put(2, SUBMARINE);
        shipTypeMap.put(3, CRUISER);
        shipTypeMap.put(4, BATTLESHIP);
    }

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public static ShipType getBySize(int size) {
        return shipTypeMap.get(size);
    }

    public int getSize() {
        return size;
    }
}
