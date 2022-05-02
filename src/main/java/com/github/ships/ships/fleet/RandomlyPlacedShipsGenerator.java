package com.github.ships.ships.fleet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RandomlyPlacedShipsGenerator {

    private final int playerId;
    private final int boardWidth;
    private final int boardHeight;
    private List<Integer> shipSizes;
    private Set<Integer> occupiedCells;


    public RandomlyPlacedShipsGenerator(int playerId, int boardWidth, int boardHeight) {
        this.playerId = playerId;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        occupiedCells = new HashSet<>();
        shipSizes = new ArrayList<>();
        shipSizes.addAll(List.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4));
    }

    public List<Ship> produceShips() {
        List<Ship> ships = new ArrayList<>();
        Random random = new Random();
//        while (shipSizes.size() > 0) {
        int randomShipSizeIndex = random.nextInt(shipSizes.size());
        ships.add(generateShip(shipSizes.get(randomShipSizeIndex)));
        shipSizes.remove(randomShipSizeIndex);
//        }
        return ships;
    }

    private Ship generateShip(int shipSize) {
        List<List<Integer>> allShipPositions = new ArrayList<>();
        addAllHorizontalPositions(allShipPositions, shipSize);
        if(shipSize > 1) addAllVerticalPositions(allShipPositions, shipSize);
//TODO add to set masts and adjacent cells
        return null;
    }

    private void addAllHorizontalPositions(List<List<Integer>> allShipPositions, int shipSize) {
        List<List<Integer>> allHorizontalPositions = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(boardWidth * boardHeight - shipSize + 1).
                forEach(i -> addHorizontalShipPosition(i, shipSize, allHorizontalPositions));
        allHorizontalPositions.stream().filter(p -> isShipInOneBoardLine(p) && !isAnyPositionOccupied(p)).
                forEach(p -> allShipPositions.add(p));
    }

    private void addHorizontalShipPosition(Integer cellId, int shipSize, List<List<Integer>> allHorizontalPositions) {
        allHorizontalPositions.add(Stream.iterate(cellId, i -> i + 1).limit(shipSize).collect(Collectors.toList()));
    }

    private boolean isShipInOneBoardLine(List<Integer> shipCells) {
        return shipCells.get(shipCells.size() - 1) % boardWidth == 0 ||
                !shipCells.stream().anyMatch(i -> i % boardWidth == 0);
    }

    private void addAllVerticalPositions(List<List<Integer>> allShipPositions, int shipSize) {
        List<List<Integer>> allVerticalPositions = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(boardWidth * boardHeight - (shipSize - 1) * boardWidth).
                forEach(i -> addVerticalShipPosition(i, shipSize, allVerticalPositions));
        allVerticalPositions.stream().filter(p -> !isAnyPositionOccupied(p)).forEach(p -> allShipPositions.add(p));
    }

    private void addVerticalShipPosition(Integer cellId, int shipSize, List<List<Integer>> allVerticalPositions) {
        allVerticalPositions.add(Stream.iterate(cellId, i -> i + boardWidth).limit(shipSize).
                collect(Collectors.toList()));
    }

    private boolean isAnyPositionOccupied(List<Integer> masts) {
        return masts.stream().anyMatch(m -> isPositionOccupied(m));
    }

    private boolean isPositionOccupied(Integer mast) {
        return occupiedCells.stream().anyMatch(m -> m.equals(mast));
    }
}
