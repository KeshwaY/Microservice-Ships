package com.github.ships.ships.shot;

import java.util.HashMap;
import java.util.Map;

public enum ShotLegality {
    LEGAL, ILLEGAL;

    private final static Map<Boolean, ShotLegality> shotsLegality;

    static {
        shotsLegality = new HashMap<>();
        shotsLegality.put(true, ShotLegality.LEGAL);
        shotsLegality.put(false, ShotLegality.ILLEGAL);
    }

    public static ShotLegality obtainShotLegality(boolean isLegal) {
        return shotsLegality.get(isLegal);
    }
}
