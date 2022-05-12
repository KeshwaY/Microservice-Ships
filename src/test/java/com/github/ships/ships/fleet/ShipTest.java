package com.github.ships.ships.fleet;

import com.github.ships.ships.ShotResult;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

@Listeners({MockitoTestNGListener.class})
public class ShipTest {

    private Ship ship;

    @BeforeMethod
    public void setUp() {
        this.ship = new Ship(ShipType.SUBMARINE,
                new HashMap<Integer, MastState>() {{
                    put(3, MastState.ALIVE);
                    put(4, MastState.ALIVE);
                }},
                Arrays.asList(2, 12, 13, 14, 15, 5));
    }

    @Test
    public void shouldPlaceShotAndMiss() {
        //given
        //when
        ShotResultDto shotResultDto = ship.placeShot(1);
        //then
        assertEquals(shotResultDto.getShotResult(), ShotResult.MISS);
    }

    @Test
    public void shouldPlaceShotAndHitShip() {
        //given
        //when
        ShotResultDto shotResultDto = ship.placeShot(3);
        //then
        assertEquals(shotResultDto.getShotResult(), ShotResult.SHIP_HIT);
    }

    @Test
    public void shouldPlaceShotAndSunkShip() {
        //given
        //when
        ship.placeShot(4);
        ShotResultDto shotResultDto = ship.placeShot(3);
        //then
        assertEquals(shotResultDto.getShotResult(), ShotResult.SHIP_SUNK);
    }

    @Test
    public void shouldRetrieveProperOccupiedCells() {
        //given
        //when
        List<Integer> actual = ship.retrieveOccupiedCells();
        //then
        assertTrue(actual.containsAll(List.of(2, 12, 13, 14, 15, 5)));
    }
    
    @Test
    public void shouldReturnFalseIfShipIsNotSunk() {
        //given
        //when
        boolean actual = ship.isDead();
        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrueIfShipIsSunk() {
        //given
        ship.getMasts().put(3, MastState.HIT);
        ship.getMasts().put(4, MastState.HIT);
        //when
        boolean actual = ship.isDead();
        //then
        assertTrue(actual);
    }

}