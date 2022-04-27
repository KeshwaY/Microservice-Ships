package com.github.ships.ships;

import java.util.HashMap;
import java.util.Map;

public enum Cell {
    WATER_HIT(false),
    WATER_NOT_HIT_ADJACENT(true),
    WATER_NOT_HIT_USUAL(true),
    MAST_ALIVE(true),
    MAST_DEAD(false);

    /**
     * key: a cell
     * value: type of cell after a hit
     */
    private static final Map<Cell, Cell> cellsAfterHit;

    private final boolean canBeHit;

    static {
        cellsAfterHit = new HashMap<>();
        cellsAfterHit.put(WATER_HIT, WATER_HIT);
        cellsAfterHit.put(WATER_NOT_HIT_ADJACENT, WATER_HIT);
        cellsAfterHit.put(WATER_NOT_HIT_USUAL, WATER_HIT);
        cellsAfterHit.put(MAST_ALIVE, MAST_DEAD);
        cellsAfterHit.put(MAST_DEAD, MAST_DEAD);
    }

    Cell(boolean canBeHit) {
        this.canBeHit = canBeHit;
    }

    public Cell cellAfterHit() {
        return cellsAfterHit.get(this);
    }

    public boolean canBeHit() {
        return canBeHit;
    }
}
