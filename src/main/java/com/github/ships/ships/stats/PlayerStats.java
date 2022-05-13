package com.github.ships.ships.stats;

import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Map;

@Document
@NoArgsConstructor @Getter @Setter
class PlayerStats {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull @DocumentReference
    private User player;

    @NonNull
    private Integer winnings;

    PlayerStats(User player) {
        this.player = player;
        this.winnings = 0;
    }

    PlayerStats(User player, Integer winnings) {
        this.player = player;
        this.winnings = winnings;
    }

    void increment() {
        winnings++;
    }

    @Override
    public String toString() {
        return String.format("%s - winnings: %d", this.player.getName(), this.winnings);
    }
}
