package com.github.ships.ships;

import lombok.Data;
import lombok.NonNull;

@Data
public class BoardGetDTO {
    @NonNull private Integer width;
    @NonNull private Integer height;
}
