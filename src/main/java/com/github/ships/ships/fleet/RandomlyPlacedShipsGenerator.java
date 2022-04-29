package com.github.ships.ships.fleet;

import java.util.*;
import java.util.function.Consumer;
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
//        while (shipSizes.size() > 0) {
        int shipSizePosition = new Random().nextInt(shipSizes.size());
        ships.add(generateShip(shipSizes.get(shipSizePosition)));
        shipSizes.remove(shipSizePosition);
//        }
        return ships;
    }

    private Ship generateShip(int shiSize) {
        List<List<Integer>> allShipPositions = new ArrayList<>();
        addAllHorizontalPositions(allShipPositions, shiSize);
//TODO add vertical positions and to be continued
        return null;
    }

    private void addAllHorizontalPositions(List<List<Integer>> allShipPositions, int shipSize) {
        List<List<Integer>> allHorizontalPositions = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(boardWidth * boardHeight).forEach(i -> addShipPosition(i, shipSize,
                allHorizontalPositions));
        List<List<Integer>> allValidHorizontalPositions =
                allHorizontalPositions.stream().filter(l -> isShipInOneBoardLine(l) && isAllFieldsInBoardRange(l)).collect(Collectors.toList());
        allValidHorizontalPositions.forEach(System.out::println);
        System.err.println(shipSize);
    }

    private void addShipPosition(Integer cellId, int shipSize, List<List<Integer>> allHorizontalPositions) {
        allHorizontalPositions.add(Stream.iterate(cellId, i -> i + 1).limit(shipSize).collect(Collectors.toList()));
    }

    private boolean isShipInOneBoardLine(List<Integer> shipCells) {
        return shipCells.get(shipCells.size() - 1) % boardWidth == 0 ||
                !shipCells.stream().anyMatch(i -> i % boardWidth == 0);
    }

    private boolean isAllFieldsInBoardRange(List<Integer> shipCells) {
        return shipCells.stream().allMatch(i -> i <= boardWidth * boardHeight);
    }
}
