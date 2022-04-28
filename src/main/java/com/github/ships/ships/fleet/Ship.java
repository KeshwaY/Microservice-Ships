package com.github.ships.ships.fleet;

import com.github.ships.ships.shot.StatusOfLegalShot;
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
        this.adjacentCells = initAdjacentCells(cellsIDs);
    }

    boolean isAlive() {
        return masts.values()
                    .stream()
                    .anyMatch(mastState -> mastState == MastState.ALIVE);
    }

    StatusOfLegalShot placeShot(int cellID) {
        if(masts.containsKey(cellID)) {
            changeMastState(cellID);
            if(!isAlive()) return StatusOfLegalShot.SUNK_SHIP;
            return StatusOfLegalShot.HIT_MAST;
        }
        return StatusOfLegalShot.HIT_WATER;
    }

    boolean containsCellId(int cellID) {
        return masts.containsKey(cellID);
    }

    private Map<Integer, MastState> initMasts(List<Integer> cellsIDs) {
        HashMap<Integer, MastState> masts = new HashMap<>();
        cellsIDs.forEach(cellID -> masts.put(cellID, MastState.ALIVE));
        return masts;
    }

    private Set<Integer> initAdjacentCells(List<Integer> cellsIDs) {
        return Set.of();
    }

    private boolean changeMastState(int cellID) {
        if (containsMast(cellID)) {
            masts.put(cellID, MastState.HIT);
            return true;
        }
        return false;
    }

    private boolean containsMast(int cellID) {
        return masts.containsKey(cellID);
    }

    public Collection<Integer> retrieveMastsCellIDs(int cellId) {
        return new ArrayList(masts.keySet());
    }

    Set<Integer> retrieveAdjacentsCellIDs(int cellId) {
        return new HashSet<>(adjacentCells);
    }
}
