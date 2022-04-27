package com.github.ships.ships.shot;

import lombok.Data;

import java.util.List;

@Data
public class ShotResult {

    private ShotLegality shotLegality;

    private StatusOfLegalShot statusOfLegalShot;

    private int cellIDofLegalShot;
    private List<Integer> shipSunk;
    private List<Integer> adjWaterOfShipSunk;

}
