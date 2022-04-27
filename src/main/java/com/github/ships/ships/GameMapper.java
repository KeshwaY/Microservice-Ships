package com.github.ships.ships;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
interface GameMapper {

    GameCreatedDTO gameToGameCreatedDTO(Game game, BoardGetDTO boardGetDTO);

}
