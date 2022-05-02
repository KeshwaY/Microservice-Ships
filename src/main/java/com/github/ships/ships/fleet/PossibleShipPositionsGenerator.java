package com.github.ships.ships.fleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Basing on the board dimensions, provides all legal ship positions.
 *
 * @author Maciej Blok
 */
class PossibleShipPositionsGenerator {

    private final int boardWidth;
    private final int boardHeight;

    PossibleShipPositionsGenerator(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    /**
     * Extracts all horizontal and vertical legal positions of the ship and return them in form of the list.
     *
     * @param shipSize      size of the ship to be placed.
     * @param occupiedCells {@link java.util.Collection} of fields that are already occupied and cannot be occupied
     *                      by currently generated ship.
     * @return {@link java.util.List}
     */
    List<List<Integer>> generateShipPositions(int shipSize, Set<Integer> occupiedCells) {
        List<List<Integer>> allLegalShipPositions = new ArrayList<>();
        addAllHorizontalPositions(allLegalShipPositions, shipSize, occupiedCells);
        if (shipSize > 1) addAllVerticalPositions(allLegalShipPositions, shipSize, occupiedCells);
        return allLegalShipPositions;
    }

    private void addAllHorizontalPositions(List<List<Integer>> allShipPositions, int shipSize,
                                           Set<Integer> occupiedCells) {
        List<List<Integer>> allHorizontalPositions = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(boardWidth * boardHeight - shipSize + 1).
                forEach(i -> addHorizontalShipPosition(i, shipSize, allHorizontalPositions));
        allHorizontalPositions.stream().filter(p -> isShipInOneBoardLine(p) && !isAnyPositionOccupied(p,
                        occupiedCells)).
                forEach(p -> allShipPositions.add(p));
    }

    private void addHorizontalShipPosition(Integer cellId, int shipSize, List<List<Integer>> allHorizontalPositions) {
        allHorizontalPositions.add(Stream.iterate(cellId, i -> i + 1).limit(shipSize).collect(Collectors.toList()));
    }

    private boolean isShipInOneBoardLine(List<Integer> shipCells) {
        return shipCells.get(shipCells.size() - 1) % boardWidth == 0 ||
                !shipCells.stream().anyMatch(i -> i % boardWidth == 0);
    }

    private void addAllVerticalPositions(List<List<Integer>> allShipPositions, int shipSize,
                                         Set<Integer> occupiedCells) {
        List<List<Integer>> allVerticalPositions = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(boardWidth * boardHeight - (shipSize - 1) * boardWidth).
                forEach(i -> addVerticalShipPosition(i, shipSize, allVerticalPositions));
        allVerticalPositions.stream().filter(p -> !isAnyPositionOccupied(p, occupiedCells)).
                forEach(p -> allShipPositions.add(p));
    }

    private void addVerticalShipPosition(Integer cellId, int shipSize, List<List<Integer>> allVerticalPositions) {
        allVerticalPositions.add(Stream.iterate(cellId, i -> i + boardWidth).limit(shipSize).
                collect(Collectors.toList()));
    }

    private boolean isAnyPositionOccupied(List<Integer> masts, Set<Integer> occupiedCells) {
        return masts.stream().anyMatch(m -> isPositionOccupied(m, occupiedCells));
    }

    private boolean isPositionOccupied(Integer mast, Set<Integer> occupiedCells) {
        return occupiedCells.stream().anyMatch(m -> m.equals(mast));
    }
}
