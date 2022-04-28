package com.github.ships.ships.shot;

public class ProcedureAfterDefiningLegalShot implements ProcedureAfterDefinedShotLegality {

    @Override
    public ShotResult perform() {
        ShotResult shotResult = new ShotResult();
        shotResult.setShotLegality(ShotLegality.LEGAL);
        return shotResult;
    }
}
