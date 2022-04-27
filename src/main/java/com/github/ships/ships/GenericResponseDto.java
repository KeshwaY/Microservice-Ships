package com.github.ships.ships;

import lombok.Data;
import lombok.NonNull;

@Data
public class GenericResponseDto {
    @NonNull private String message;
}
