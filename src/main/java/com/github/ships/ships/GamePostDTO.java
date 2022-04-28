package com.github.ships.ships;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class GamePostDTO {

    //TODO: it doesn't prevent from creating the game
    //TODO: with values from the outside of the range
    @Min(value = 10)
    @Max(value = 20)
    private int height;

    @Min(value = 10)
    @Max(value = 20)
    private int width;
}
