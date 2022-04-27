package com.github.ships.ships.shot;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ShotPostDTO {

    private String gameId;
    private int playerId;
    private int cellIdToPlaceShot;

}
