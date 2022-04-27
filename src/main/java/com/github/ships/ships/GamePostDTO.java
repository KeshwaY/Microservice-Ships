package com.github.ships.ships;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class GamePostDTO {

    @Min(value = 10)
    @Max(value = 20)
    private int height;

    @Min(value = 10)
    @Max(value = 20)
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
