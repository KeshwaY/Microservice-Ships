package com.github.ships.ships.fleet;

import lombok.*;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Ship {
    private ShipType type;
    private Map<Integer, MastState> masts;
    private Collection<Integer> extraOccupied;

    public boolean isAlive() {
        return masts.values().stream().anyMatch(m -> m == MastState.ALIVE);
    }
}
