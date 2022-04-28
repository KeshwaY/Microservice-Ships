package com.github.ships.ships.fleet;

import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Fleet {

    @Id
    private String id;

    @NonNull private Collection<Ship> ships;

    @DocumentReference
    @NonNull private User player;
}
