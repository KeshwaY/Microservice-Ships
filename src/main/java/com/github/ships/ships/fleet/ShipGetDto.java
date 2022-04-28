package com.github.ships.ships.fleet;

import lombok.Data;

import java.util.Collection;

@Data
public class ShipGetDto {
    private final ShipType type;
    private final Collection<Integer> masts;
}
