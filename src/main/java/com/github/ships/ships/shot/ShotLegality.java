package com.github.ships.ships.shot;

import java.util.HashMap;
import java.util.Map;

public enum ShotLegality {
    LEGAL(new ProcedureAfterDefiningLegalShot()),
    ILLEGAL(new ProcedureAfterDefiningIllegalShot());

    private final static Map<Boolean, ShotLegality> shotsLegality;

    private final ProcedureAfterDefinedShotLegality afterwardsProcedure;

    static {
        shotsLegality = new HashMap<>();
        shotsLegality.put(true, ShotLegality.LEGAL);
        shotsLegality.put(false, ShotLegality.ILLEGAL);
    }

    ShotLegality(ProcedureAfterDefinedShotLegality afterwardsProcedure) {
        this.afterwardsProcedure = afterwardsProcedure;
    }

    public static ShotLegality obtainShotLegality(boolean isLegal) {
        return shotsLegality.get(isLegal);
    }

    public ShotResult performAfterwardsProcedure(ShotPostDTO shotPostDTO) {
        return afterwardsProcedure.perform(shotPostDTO);
    }
}
