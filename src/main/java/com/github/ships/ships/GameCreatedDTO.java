package com.github.ships.ships;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class GameCreatedDTO {
    @NonNull private String id;
    @JsonProperty("board")
    @NonNull private BoardGetDTO boardGetDTO;
}
