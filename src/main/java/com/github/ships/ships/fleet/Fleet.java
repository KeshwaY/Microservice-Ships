package com.github.ships.ships.fleet;

import com.github.ships.ships.shot.StatusOfLegalShot;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Document
public class Fleet {

    @Id
    private String id;

    @NonNull
    private String gameId;
    @NonNull
    private Integer playerId;

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

    StatusOfLegalShot placeShot(int cellId) {
        AtomicReference<StatusOfLegalShot> atomicShotResult = new AtomicReference<>(StatusOfLegalShot.HIT_WATER);
        ships.stream().filter(s -> s.containsCellId(cellId)).findFirst().
                ifPresent(ship -> atomicShotResult.set(ship.placeShot(cellId)));
        StatusOfLegalShot shotResult = atomicShotResult.get();
        if (shotResult == StatusOfLegalShot.SUNK_SHIP && !isAlive()) shotResult = StatusOfLegalShot.SUNK_FLEET;
        return shotResult;
    }

    List<Integer> retrieveSunkedShipMastsCellIDs(int cellId) {
        List<Integer> sunkedShipAdjacentCellIDs = new ArrayList<>();
        ships.stream().filter(s -> s.containsCellId(cellId)).findFirst().
                ifPresent(s -> sunkedShipAdjacentCellIDs.addAll(s.retrieveMastsCellIDs(cellId)));
        return sunkedShipAdjacentCellIDs;
    }

    List<Integer> retrieveSunkedShipAdjacentsCellIDs(int cellId) {
        List<Integer> sunkedShipAdjacentCellIDs = new ArrayList<>();
        ships.stream().filter(s -> s.containsCellId(cellId)).findFirst().
                ifPresent(s -> sunkedShipAdjacentCellIDs.addAll(s.retrieveAdjacentsCellIDs(cellId)));
        return sunkedShipAdjacentCellIDs;
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
    }

    private boolean isAlive() {
        return ships.stream().anyMatch(s -> s.isAlive());
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
}
