package com.github.ships.ships.fleet;

import com.github.ships.ships.users.User;

import java.util.*;

/**
 * Basing on ship sizes and board dimensions, provides randomly generated ship positions.
 *
 * @author Maciej Blok
 */
class RandomlyPlacedShipsGenerator {

    private final User user;
    private final int boardWidth;
    private final int boardHeight;
    private final List<Integer> shipSizes;
    private final Set<Integer> occupiedCells;

    RandomlyPlacedShipsGenerator(int boardWidth, int boardHeight, User user) {
        this.user = user;
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
    Fleet generateRandomlyPlacedFleet() {
        Collection<Ship> ships = new ArrayList<>();
        while (shipSizes.size() > 0) {
            int randomShipSizeIndex = new Random().nextInt(shipSizes.size());
            ships.add(generateShip(shipSizes.get(randomShipSizeIndex)));
            shipSizes.remove(randomShipSizeIndex);
        }
        return new Fleet(ships, user);
    }

    private Ship generateShip(int shipSize) {
        List<List<Integer>> allLegalShipPositions = new PossibleShipPositionsGenerator(boardWidth, boardHeight).
                generateShipPositions(shipSize, occupiedCells);
        List<Integer> randomShipPosition =
                allLegalShipPositions.get(new Random().nextInt(0, allLegalShipPositions.size()));
        ShipTemplate shipTemplate = new ShipTemplate(randomShipPosition, boardWidth, boardHeight);
        occupiedCells.addAll(shipTemplate.getAdjacentCells());
        return new Ship(ShipType.getBySize(shipTemplate.getMasts().size()),
                shipTemplate.getMasts(), shipTemplate.getAdjacentCells());
    }
}
