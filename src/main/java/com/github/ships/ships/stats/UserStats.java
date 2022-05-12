package com.github.ships.ships.stats;

import com.github.ships.ships.ShotResult;
import com.github.ships.ships.users.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.EnumMap;

@Document
@Getter @Setter
class UserStats {

    @Id
    private String id;

    @DocumentReference
    private User player;

    @NonNull
    private EnumMap<ShotResult, Integer> shotResults = new EnumMap<>(ShotResult.class);

    UserStats() {
        shotResults.put(ShotResult.MISS, 0);
        shotResults.put(ShotResult.SHIP_HIT, 0);
        shotResults.put(ShotResult.SHIP_SUNK, 0);
        shotResults.put(ShotResult.FLEET_SUNK, 0);
    }

    void increment(ShotResult shotResult) {
        Integer i = shotResults.get(shotResult);
        i++;
    }

}
