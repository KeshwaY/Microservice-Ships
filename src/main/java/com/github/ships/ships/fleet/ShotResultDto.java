package com.github.ships.ships.fleet;

import com.github.ships.ships.ShotResult;
import com.github.ships.ships.board.Cell;
import lombok.Data;

import java.util.Collection;

@Data
public class ShotResultDto {
    private final ShotResult shotResult;
    private final Collection<Integer> cells;
}
