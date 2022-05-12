package com.github.ships.ships.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardPostDto {
    @Min(10)
    @Max(15)
    private int width;
    @Min(10)
    @Max(15)
    private int height;
}
