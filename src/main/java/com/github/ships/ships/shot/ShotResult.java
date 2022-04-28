package com.github.ships.ships.shot;

import lombok.Data;

import java.util.List;

@Data
public class ShotResult {

    /**
     * 1. Is always defined
     * 2. Can be LEGAL || ILLEGAL
     * 3. If ILLEGAL, the rest of fields is undefined
     * */
    private ShotLegality shotLegality;

    /**
     * 1. Defined always if shotLegality == LEGAL
     * 2. Can be HIT_MAST, HIT_WATER, SUNK_SHIP, SUNK_FLEET
     * 3. IF hit Cell was WATER_NOT_HIT_ADJACENT || WATER_NOT_HIT_USUAL:
     *      - defined by Shot as HIT_WATER
     *    ELSE:
     *      - defined by Fleet as HIT_MAST || SUNK_SHIP || SUNK_FLEET
     */
    private StatusOfLegalShot statusOfLegalShot;

    /**
     * 1. Defined always if shotLegality == LEGAL.
     * 2. Defined by Shot
     */
    private int cellIDofLegalShot;

    /**
     * 1. Defined always if:
     *     - shotLegality == LEGAL.
     *       &&
     *     - ship sunk
     * 2. Defined by Fleet
     */
    private List<Integer> shipSunk;

    /**
     * 1. Defined always if:
     *     - shotLegality == LEGAL.
     *       &&
     *     - ship sunk
     * 2. Defined by Fleet
     */
    private List<Integer> adjWaterOfShipSunk;

}
