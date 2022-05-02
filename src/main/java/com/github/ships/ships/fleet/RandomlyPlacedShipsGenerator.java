package com.github.ships.ships.fleet;

import java.util.*;

/**
 * Basing on ship sizes and board dimensions, provides randomly generated ship positions.
 *
 * @author Maciej Blok
 */
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

    /**
     * Provides randomly generated ship positions.
     *
     * @return {@link java.util.List}
     */
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
        List<List<Integer>> allLegalShipPositions = new PossibleShipPositionsGenerator(boardWidth, boardHeight).
                generateShipPositions(shipSize, occupiedCells);
//TODO add to set masts and adjacent cells
        return null;
    }
}
