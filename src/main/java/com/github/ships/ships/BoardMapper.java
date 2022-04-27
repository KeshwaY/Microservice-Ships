package com.github.ships.ships;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface BoardMapper {
    BoardGetDTO boardToBoardGetDTO(Board board);
}
