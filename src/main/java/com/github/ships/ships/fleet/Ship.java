package com.github.ships.ships.fleet;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Ship {

    @Id
    private String id;

    @NonNull
    private String gameId;
    @NonNull private Integer playerId;

    private final Map<Integer, MastState> masts;
    private Set<Integer> adjacentCells;

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

    public List<Collection<Integer>> placeShot(int cellID) {
        if(masts.containsKey(cellID)) {
            changeMastState(cellID);
            if(!isAlive()) return List.of(adjacentCells);
            return List.of(List.of(cellID));
        }
        return List.of();
    }

    public boolean changeMastState(int cellID) {
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
