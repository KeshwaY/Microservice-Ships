package com.github.ships.ships.fleet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ship {

    private final Map<Integer, MastState> masts;

    public Ship(List<Integer> cellsIDs) {
        this.masts = initMasts(cellsIDs);
    }

    private Map<Integer, MastState> initMasts(List<Integer> cellsIDs) {
        HashMap<Integer, MastState> masts = new HashMap<>();
        cellsIDs.forEach(cellID -> masts.put(cellID, MastState.ALIVE));
        return masts;
    }

    public boolean isAlive() {
        return masts.values()
                    .stream()
                    .anyMatch(mastState -> mastState == MastState.ALIVE);
    }

    public boolean placeShot(int cellID) {
        if (containsMast(cellID)) {
            masts.put(cellID, MastState.HIT);
            return true;
        }
        return false;
    }

    private boolean containsMast(int cellID) {
        return masts.containsKey(cellID);
    }
}
