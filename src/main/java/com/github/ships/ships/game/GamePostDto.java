package com.github.ships.ships.game;

import com.github.ships.ships.board.BoardPostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class GamePostDto {
    private BoardPostDto board;
}
