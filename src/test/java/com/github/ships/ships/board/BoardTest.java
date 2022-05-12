package com.github.ships.ships.board;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.*;

@Test
public class BoardTest {
    @DataProvider
    public static Object[][] inRangeIndex() {
        return new Object[][]{
                {10, 10, 2}
                , {10, 10, 1}
                , {10, 10, 100}
                , {10, 10, 50}
                , {15, 15, 150}
        };
    }

    @Test(dataProvider = "inRangeIndex")
    public void containsIndexShouldReturnTrueIfIndexIsInRange(int width, int height, int index) {
        //given
        Board board = new Board();
        board.setWidth(width);
        board.setHeight(height);
        board.setCells(IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)));
        //when
        boolean actual = board.containsIndex(index);
        //then
        assertTrue(actual);
    }

    @DataProvider
    public static Object[][] outOfRangeIndex() {
        return new Object[][]{
                {10, 10, -1}
                , {10, 10, 200}
                , {10, 10, 101}
                , {10, 10, 0}
                , {15, 15, 1000}
        };
    }

    @Test(dataProvider = "outOfRangeIndex")
    public void containsIndexShouldReturnFalseIfIndexIsOutOfRange(int width, int height, int index) {
        //given
        Board board = new Board();
        board.setWidth(width);
        board.setHeight(height);
        board.setCells(IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)));
        //when
        boolean actual = board.containsIndex(index);
        //then
        assertFalse(actual);
    }

    @Test(dataProvider = "inRangeIndex")
    public void shouldReturnTrueFroValidShot(int width, int height, int index) {
        //given
        Board board = new Board();
        board.setWidth(width);
        board.setHeight(height);
        board.setCells(IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)));
        //when
        boolean actual = board.isValidShot(index);
        //then
        assertTrue(actual);
    }

    @DataProvider
    public static Object[][] invalidShots () {
        return new Object [][] {
        {1}
        ,{2}
        ,{10}
        };
    }

    @Test(dataProvider = "invalidShots")
    public void shoulReturnFalseForInvalidShot(int index) {
        //given
        Board board = new Board();
        board.setWidth(10);
        board.setHeight(10);
        board.setCells(IntStream.rangeClosed(1, 10 * 10).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)));
        board.getCells().put(index, Cell.HIT);
        //when
        boolean actual = board.isValidShot(index);
        //then
        assertFalse(actual);
    }

}
