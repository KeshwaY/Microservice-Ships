package com.github.ships.ships.FAKEFLEET;

import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import com.github.ships.ships.shot.StatusOfLegalShot;

import java.util.List;

public class FleetService {

    public ShotResult placeShot(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_SHIP);
        shotResult.setShipSunk(List.of(1, 3, 4));
        shotResult.setAdjWaterOfShipSunk(List.of(6, 2, 4, 9));
        return shotResult;
    }
}
