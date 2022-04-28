package com.github.ships.ships.shot;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.fleet.FleetService;

public class ProcedureAfterDefiningIllegalShot implements ProcedureAfterDefinedShotLegality {

    @Override
    public ShotResult perform(ShotPostDTO shotPostDTO, FleetService fleetService) {
        ShotResult shotResult = new ShotResult();
        shotResult.setShotLegality(ShotLegality.ILLEGAL);
        return shotResult;
    }
}
