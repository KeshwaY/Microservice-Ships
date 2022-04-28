package com.github.ships.ships.board;

import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Map;

@Document
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Board {
    @Id
    private String id;

    @DocumentReference
    @NonNull private User player;

    @NonNull private Integer width;
    @NonNull private Integer height;

    @NonNull private Map<Integer, Cell> cells;
}
