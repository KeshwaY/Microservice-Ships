package com.github.ships.ships.fleet;

import lombok.Data;

import java.util.Collection;

@Data
public class FleetGetDto {
    private final Collection<ShipGetDto> ships;
}
