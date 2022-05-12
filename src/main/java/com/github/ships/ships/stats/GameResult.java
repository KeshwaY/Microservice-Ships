package com.github.ships.ships.stats;

import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@RequiredArgsConstructor @Getter @Setter
class GameResult {

    @Id
    private String id;

    @DocumentReference
    private Game game;

    @DocumentReference
    private User winner;
}
