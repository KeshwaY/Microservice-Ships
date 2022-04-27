package com.github.ships.ships.shot;

import lombok.Data;

@Data
public class ShotPostDTO {

    private String gameId;
    private int playerId;
    private int cellIdToPlaceShot;

}
