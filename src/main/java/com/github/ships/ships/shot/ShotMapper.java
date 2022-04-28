package com.github.ships.ships.shot;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface ShotMapper {
    ShotResultDTO shotResultToShotResultDTO(ShotResult result);
}
