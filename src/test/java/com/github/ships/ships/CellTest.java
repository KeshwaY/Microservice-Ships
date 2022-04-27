package com.github.ships.ships;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CellTest {

    @DataProvider
    public Object[][] cellsAfterHit() {
        return new Object[][] {
                {Cell.WATER_HIT, Cell.WATER_HIT},
                {Cell.WATER_NOT_HIT_ADJACENT, Cell.WATER_HIT},
                {Cell.WATER_NOT_HIT_USUAL, Cell.WATER_HIT},
                {Cell.MAST_ALIVE, Cell.MAST_DEAD},
                {Cell.MAST_DEAD, Cell.MAST_DEAD},
        };
    }

    @Test(dataProvider = "cellsAfterHit")
    public void testCellAfterHit(Cell cellBeforeHit, Cell cellAfterHit) {
        Assert.assertEquals(cellBeforeHit.cellAfterHit(), cellAfterHit);
    }
}
