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

    private final int boardWidth;
    private final int boardHeight;

    private final Map<Integer, MastState> masts;
    private Set<Integer> adjacentCells;

    public Ship(List<Integer> cellsIDs, int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.masts = initMasts(cellsIDs);
        this.adjacentCells = new HashSet<>();
        initiateAdjacentCels();
    }

    private void initiateAdjacentCels() {
        masts.keySet().forEach(i -> addOccupiedFields(i));
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

    Collection<Integer> retrieveMastsCellIDs(int cellId) {
        return new ArrayList(masts.keySet());
    }

    Set<Integer> retrieveAdjacentsCellIDs(int cellId) {
        return new HashSet<>(adjacentCells);
    }

    private Map<Integer, MastState> initMasts(List<Integer> cellsIDs) {
        HashMap<Integer, MastState> masts = new HashMap<>();
        cellsIDs.forEach(cellID -> masts.put(cellID, MastState.ALIVE));
        return masts;
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

    private void addOccupiedFields(int cellID) {
        adjacentCells.add(cellID);
        addTopAndBottomFields(cellID);
        addLeftFields(cellID);
        addRightFields(cellID);
    }

    private void addTopAndBottomFields(int cellID) {
        addCellIdIfInRange(cellID - boardWidth);
        addCellIdIfInRange(cellID + boardWidth);
    }

    private void addLeftFields(int cellID) {
        if (!isCellOnLeftEdge(cellID)) {
            addCellIdIfInRange(cellID - 1);
            addCellIdIfInRange(cellID - boardWidth - 1);
            addCellIdIfInRange(cellID + boardWidth - 1);
        }
    }

    private boolean isCellOnLeftEdge(int cellID) {
        return (cellID - 1) % boardWidth == 0;
    }

    private void addRightFields(int cellID) {
        if (!isCellOnRightEdge(cellID)) {
            addCellIdIfInRange(cellID + 1);
            addCellIdIfInRange(cellID - boardWidth + 1);
            addCellIdIfInRange(cellID + boardWidth + 1);
        }
    }

    private boolean isCellOnRightEdge(int cellID) {
        return cellID % boardWidth == 0;
    }

    private void addCellIdIfInRange(int cellID) {
        if (isInBoardRange(cellID)) adjacentCells.add(cellID);
    }

    private boolean isInBoardRange(int cellID) {
        return cellID > 0 && cellID <= boardWidth * boardHeight;
    }
}
