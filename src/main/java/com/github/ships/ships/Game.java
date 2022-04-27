package com.github.ships.ships;

import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Document
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Game {

    @Id
    private String id;

    @DocumentReference(lookup = "{'gameId':?#{#self._id} }")
    @NonNull private Collection<Board> boards;
}
