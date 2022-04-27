package com.github.ships.ships;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Board {

    @Id
    private String id;

    @NonNull private String gameId;
    @NonNull private Integer playerId;

    @NonNull private Integer width;
    @NonNull private Integer height;
    @NonNull private Map<Integer, Cell> cells;
}
