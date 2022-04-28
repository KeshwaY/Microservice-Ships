package com.github.ships.ships.game;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.fleet.Fleet;
import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Document
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Game {
    @Id
    private String id;

    @DocumentReference
    @NonNull private User owner;

    @DocumentReference
    private User opponent;

    @DocumentReference
    @NonNull private Collection<Board> boards;

    @DocumentReference
    @NonNull private Collection<Fleet> fleets;

    public User getRelativeOpponent(User user) {
        return user.getEmail().equals(owner.getEmail()) ? opponent : owner;
    }
}