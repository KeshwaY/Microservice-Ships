package com.github.ships.ships;

import org.mapstruct.Mapper;

/**
 * @author Anna Ovcharenko
 */
@Mapper(
        componentModel = "spring"
)
public interface ShotMapper {
    ShotResultDTO shotResultToShotResultDTO(ShotResult result);
}
