package com.github.ships.ships;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.Cell;

import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    @org.testng.annotations.Test
    public void test() {
//        int width = 4;
//        int height = 4;
//        int maxIndex = width * height;
//        Map<Integer, Cell> cells = IntStream.rangeClosed(1, maxIndex).boxed()
//                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER));
//
//        Map<ShipType, Integer> toGenerate = new HashMap<>(
//                Map.of(
//                        ShipType.DESTROYER, 4,
//                        ShipType.SUBMARINE, 3,
//                        ShipType.CRUISER, 2,
//                        ShipType.BATTLESHIP, 1
//                )
//        );
//
//
//        for (Map.Entry<ShipType, Integer> shipTypeIntegerEntry : toGenerate.entrySet()) {
//            ShipType shipType = shipTypeIntegerEntry.getKey();
//            int numberToGenerate = shipTypeIntegerEntry.getValue();
//            boolean wasGenerated = false;
//            while (!wasGenerated) {
//                Direction direction = new Random().nextInt() % 2 == 0 ? Direction.HORIZONTAL : Direction.VERTICAL;
//                int index = new Random().nextInt(1, maxIndex + 1);
//                if (direction == Direction.HORIZONTAL) {
//                    int top = index - (shipType.getSize() * width);
//                    if (top < 1) continue;
//                    System.out.println("Start" + index + " End " + top);
//                }
//                if (direction == Direction.VERTICAL) {
//                    int right = index + shipType.getSize();
//                    if (right > maxIndex) continue;
//                    System.out.println("Start" + index + " End " + right);
//                }
//                wasGenerated = true;
//            }
//        }
        /*
        & | * *
        & | * *
        & | * *
        | | * *
         */

    }

}
