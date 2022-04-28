package com.github.ships.ships.shot;

public class ProcedureAfterDefiningIllegalShot implements ProcedureAfterDefinedShotLegality {

    @Override
    public ShotResult perform() {
        ShotResult shotResult = new ShotResult();
        shotResult.setShotLegality(ShotLegality.ILLEGAL);
        return shotResult;
    }
}
