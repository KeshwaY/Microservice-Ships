package com.github.ships.ships.fleet;

import com.github.ships.ships.ShotResult;
import com.github.ships.ships.users.User;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

@Listeners({MockitoTestNGListener.class})
public class FleetTest {

    private Fleet fleet;

    @BeforeMethod
    public void setUp() {
        User user = new User("test@email.com", "testUser");
        fleet = new Fleet(new ArrayList<Ship>(), user);
        Ship ship = new Ship(ShipType.DESTROYER,
                new HashMap<Integer, MastState>() {{
                    put(1, MastState.ALIVE);
                }},
                Arrays.asList(2, 11, 12));

        Ship ship2 = new Ship(ShipType.SUBMARINE,
                new HashMap<Integer, MastState>() {{
                    put(3, MastState.ALIVE);
                    put(4, MastState.ALIVE);
                }},
                Arrays.asList(2, 12, 13, 14, 15, 5));
        fleet.setShips(List.of(ship, ship2));
    }

    @Test
    public void ShouldPlaceShotAndMissShip() {
        //given
        //when
        ShotResultDto actual = fleet.placeShot(10);
        //then
        assertEquals(actual.getShotResult(), ShotResult.MISS);
    }

    @Test
    public void ShouldPlaceShotAndHitShip() {
        //given
        //when
        ShotResultDto actual = fleet.placeShot(3);
        //then
        assertEquals(actual.getShotResult(), ShotResult.SHIP_HIT);
    }

    @Test
    public void ShouldPlaceShotAndSunkShip() {
        //given
        //when
        ShotResultDto actual = fleet.placeShot(1);
        //then
        assertEquals(actual.getShotResult(), ShotResult.SHIP_SUNK);
    }

    @Test
    public void ShouldPlaceShotAndSunkFleet() {
        //given
        //when
        fleet.placeShot(3);
        fleet.placeShot(4);
        ShotResultDto actual = fleet.placeShot(1);
        //then
        assertEquals(actual.getShotResult(), ShotResult.FLEET_SUNK);
    }

    @Test
    public void shouldRetrieveProperOccupiedCells() {
        //given
        //when
        List<Integer> actual = fleet.retrieveOccupiedCells();
        System.out.println(actual);
        //then
        assertTrue(actual.containsAll(List.of(1, 3, 4, 2, 11, 12, 13, 14, 15, 5)));
    }
}
