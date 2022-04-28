package com.github.ships.ships.shot;

import lombok.Data;

import java.util.List;

@Data
public class ShotResult {

    private ShotLegality shotLegality;

    /*
        Defined if shotLegality == LEGAL
        Defined by Board if hit cell was water.
        Defined by Fleet if hit cell was not water.
     */
    private StatusOfLegalShot statusOfLegalShot;

    /*
        Defined by Board if shotLegality is legal.
     */
    private int cellIDofLegalShot;

    /*
        Defined by Fleet in case of sunk ship.
     */
    private List<Integer> shipSunk;

    /*
        Defined by Fleet in case of sunk ship.
     */
    private List<Integer> adjWaterOfShipSunk;

}
