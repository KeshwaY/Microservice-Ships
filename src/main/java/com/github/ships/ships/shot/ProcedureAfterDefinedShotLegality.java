package com.github.ships.ships.shot;

import com.github.ships.ships.FAKEFLEET.FleetService;

public interface ProcedureAfterDefinedShotLegality {

    ShotResult perform(ShotPostDTO shotPostDTO, FleetService fleetService);
}
