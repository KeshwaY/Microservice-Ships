package com.github.ships.ships.shot;

import lombok.Data;

import java.util.Collection;

@Data
public class ShotDto {
    private final String message;
    private final Collection<CellHitDto> hits;
}
