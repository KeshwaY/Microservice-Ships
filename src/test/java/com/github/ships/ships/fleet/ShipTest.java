package com.github.ships.ships.fleet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ShipTest {
    
    @DataProvider
    public Object[][] mastsIDs() {
        return new Object[][] {
                {List.of(1, 2, 3)},
                {List.of(2)},
                {List.of(100)},
                {List.of(-1)},
        };
    }

    @Test(dataProvider = "mastsIDs")
    public void testIsAliveAfterInit(List<Integer> cellsIDs) {
        Ship ship = new Ship(cellsIDs);
        assertTrue(ship.isAlive());
    }

    @DataProvider
    public Object[][] dataForShots() {
        return new Object[][] {
                {List.of(1, 2, 3), List.of(1, 2, 3), false},
                {List.of(1, 2, 3), List.of(1, 2), true},
                {List.of(9, 4, 2), List.of(1, 2, 3), true}
        };
    }

    @Test(dataProvider = "dataForShots")
    public void testIsAliveAfterPlacingShot(List<Integer> cellsIDs,
                                            List<Integer> shots,
                                            boolean expectedResult) {
        Ship ship = new Ship(cellsIDs);
        shots.forEach(ship::placeShot);
        assertEquals(ship.isAlive(), expectedResult);
    }
}