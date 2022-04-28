package com.github.ships.ships.fleet;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Document
public class Fleet {

    @Id
    private String id;

    @NonNull
    private String gameId;
    @NonNull private Integer playerId;

    private final List<Ship> ships;
    private final List<Integer> sizesOfShipsToBePlaced;
    private final int boardWidth;
    private final int boardHeight;

    public Fleet(List<Ship> ships, int boardWidth, int boardHeight) {
        this.ships = ships;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.sizesOfShipsToBePlaced = generateSizesOfShipsToBePlaced();
        placeShipsHardcoded();
    }

    public List<Collection<Integer>> placeShot(int cellId) {
        List<Collection<Integer>> shotResult = new ArrayList<>();
        ships.forEach(s -> {
            if(!s.placeShot(cellId).get(0).isEmpty()) shotResult.addAll(s.placeShot(cellId));
        });

        return null;
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

    //TODO to be removed once better implementation is done
    private void placeShipsHardcoded() {
        ships.add(new Ship(List.of(22, 32, 42, 52)));
        ships.add(new Ship(List.of(24, 25, 26)));
        ships.add(new Ship(List.of(55, 56, 57)));
        ships.add(new Ship(List.of(10, 20)));
        ships.add(new Ship(List.of(84, 85)));
        ships.add(new Ship(List.of(71, 81)));
        ships.add(new Ship(List.of(1)));
        ships.add(new Ship(List.of(4)));
        ships.add(new Ship(List.of(39)));
        ships.add(new Ship(List.of(89)));
    }

    private List<Integer> placeShipsRandomly() {
        //TODO to be corrected and finished
//        Integer shipSize = sizesOfShipsToBePlaced.get(new Random().nextInt(0, sizesOfShipsToBePlaced.size()));
        Integer shipSize = 4;
        HashMap<Integer, List<Integer>> horizontalLegalPositions = new HashMap<>();
        IntStream.iterate(1, boardField -> boardField + 1).limit(boardWidth * boardHeight).
                forEach(boardField -> IntStream.iterate(boardField, cell -> cell + 1).limit(boardField + shipSize).
                        limit(boardWidth * boardHeight).boxed().collect(Collectors.toList()).forEach(System.err::print));
        return null;
    }

    private boolean isValidPlacement(int p) {
        //TODO implement
        return true;
    }

    private List<Integer> addOccupiedFields(int cellID, List<Integer> occupiedFields) {
        occupiedFields.add(cellID);
        addTopAndBottomFields(cellID, occupiedFields);
        addLeftFields(cellID, occupiedFields);
        addRightFields(cellID, occupiedFields);
        return occupiedFields;
    }

    private void addTopAndBottomFields(int cellID, List<Integer> occupiedFields) {
        addCellIdIfInRange(cellID - boardWidth, occupiedFields);
        addCellIdIfInRange(cellID + boardWidth, occupiedFields);
    }

    private void addLeftFields(int cellID, List<Integer> occupiedFields) {
        if (!isCellOnLeftEdge(cellID)) {
            addCellIdIfInRange(cellID - 1, occupiedFields);
            addCellIdIfInRange(cellID - boardWidth - 1, occupiedFields);
            addCellIdIfInRange(cellID + boardWidth - 1, occupiedFields);
        }
    }

    private boolean isCellOnLeftEdge(int cellID) {
        return (cellID - 1) % boardWidth == 0;
    }

    private void addRightFields(int cellID, List<Integer> occupiedFields) {
        if (!isCellOnRightEdge(cellID)) {
            addCellIdIfInRange(cellID + 1, occupiedFields);
            addCellIdIfInRange(cellID - boardWidth + 1, occupiedFields);
            addCellIdIfInRange(cellID + boardWidth + 1, occupiedFields);
        }
    }

    private boolean isCellOnRightEdge(int cellID) {
        return cellID % boardWidth == 0;
    }

    private void addCellIdIfInRange(int cellID, List<Integer> occupiedFields) {
        if (isInBoardRange(cellID)) occupiedFields.add(cellID);
    }

    private boolean isInBoardRange(int cellID) {
        return cellID > 0 && cellID <= boardWidth * boardHeight;
    }
}
