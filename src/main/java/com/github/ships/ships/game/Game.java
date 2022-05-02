package com.github.ships.ships.game;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.fleet.Fleet;
import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;
import java.util.Optional;

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

    @NonNull private boolean ownerTurn;

    public User getRelativeOpponent(User user) {
        return user.getEmail().equals(owner.getEmail()) ? opponent : owner;
    }

    public Optional<Board> getUserBoard(User user) {
        return boards.stream()
                .filter(b -> b.getPlayer().getEmail().equals(user.getEmail()))
                .findFirst();
    }

    public Optional<Fleet> getUserFleet(User user) {
        return fleets.stream()
                .filter(f -> f.getPlayer().getEmail().equals(user.getEmail()))
                .findFirst();
    }

    public boolean containsUser(User user) {
        String email = user.getEmail();
        return (owner.getEmail().equals(email) || opponent.getEmail().equals(email));
    }

//    public boolean isUserTurn(User user) {
//        return ownerTurn ? user.getEmail().equals(owner.getEmail()) : user.getEmail().equals(opponent.getEmail());
//    }

//    public void markTurn() {
//        ownerTurn = !ownerTurn;
//    }
}
