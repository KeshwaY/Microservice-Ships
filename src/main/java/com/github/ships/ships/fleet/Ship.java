package com.github.ships.ships.fleet;

import com.github.ships.ships.ShotResult;
import lombok.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Ship {
    private ShipType type;
    private Map<Integer, MastState> masts;
    private Collection<Integer> extraOccupied;

    public ShotResultDto placeShot(int index) {
        if (!masts.containsKey(index)) return new ShotResultDto(ShotResult.MISS, Set.of());
        masts.put(index, MastState.HIT);
        Set<Integer> indexes = new HashSet<>();
        indexes.add(index);
        if (!isDead()) {
            return new ShotResultDto(ShotResult.SHIP_HIT, indexes);
        }
        return getResultOfDeadShip(indexes);
    }

    private ShotResultDto getResultOfDeadShip(Set<Integer> indexes) {
        indexes.addAll(masts.keySet());
        indexes.addAll(extraOccupied);
        return new ShotResultDto(ShotResult.SHIP_SUNK, indexes);
    }

    public boolean isDead() {
        return masts.values()
                .stream()
                .allMatch(m -> m == MastState.HIT);
    }

    public List<Integer> retrieveOccupiedCells() {
        List<Integer> occupiedCells = new ArrayList<>();
        occupiedCells.addAll(masts.keySet());
        occupiedCells.addAll(occupiedCells);
        return occupiedCells;
    }
}
