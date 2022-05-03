package com.github.ships.ships.fleet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class PossibleShipPositionsGeneratorTest {

    @Test(dataProvider = "shipSizesForExtractingPositionsOnEmptyBoard")
    public void givenShipSizeExtractsAllPossiblePositionsOnEmptyBoard(int shipSize, int boardWidth, int boardHeight,
                                                                      int expectedPositionsNumber) {
        //given
        PossibleShipPositionsGenerator possibleShipPositionsGenerator = new PossibleShipPositionsGenerator(boardWidth
                , boardHeight);
        //when
        List<List<Integer>> positions =
                possibleShipPositionsGenerator.generateShipPositions(shipSize, Collections.EMPTY_SET);
        //then
        assertEquals(positions.size(), expectedPositionsNumber, "Expected found positions number to be " +
                expectedPositionsNumber + " but found " + positions.size());
    }

    @DataProvider
    public Object[][] shipSizesForExtractingPositionsOnEmptyBoard() {
        return new Object[][]{
                {1, 10, 10, 100},
                {2, 10, 10, 180},
                {3, 10, 10, 160},
                {4, 10, 10, 140},
        };
    }
}
