package com.github.ships.ships;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
interface GameMapper {

    GameGetDTO gameToGameGetDto(Game game);

}
