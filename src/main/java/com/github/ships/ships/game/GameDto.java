package com.github.ships.ships.game;

import com.github.ships.ships.board.BoardGetDto;
import com.github.ships.ships.fleet.ShipGetDto;
import lombok.Data;

import java.util.Collection;

@Data
public class GameDto {
    private final String id;
    private final BoardGetDto board;
    private final Collection<ShipGetDto> fleet;
}
