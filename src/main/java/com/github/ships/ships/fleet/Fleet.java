package com.github.ships.ships.fleet;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ShotResult;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class Fleet {

    @Id
    private String id;

    @NonNull private Collection<Ship> ships;

    @DocumentReference
    @NonNull private User player;

    public ShotResultDto placeShot(int index) {
        List<ShotResultDto> shotResult = ships.stream()
                .map(s -> s.placeShot(index))
                .distinct()
                .toList();
        if (shotResult.size() == 1) {
            return shotResult.get(0);
        }
        return getShotResultDto(shotResult);
    }

    private ShotResultDto getShotResultDto(List<ShotResultDto> shotResult) {
        ShotResultDto shotResultDto = shotResult.stream()
                .dropWhile(s -> s.getShotResult() == ShotResult.MISS)
                .findFirst().orElseThrow();
        if (shotResultDto.getShotResult() == ShotResult.SHIP_SUNK && isDead()) {
            return new ShotResultDto(ShotResult.FLEET_SUNK, shotResultDto.getCells());
        }
        return shotResultDto;
    }

    private boolean isDead() {
        return ships.stream()
                .allMatch(Ship::isDead);
    }

    List<Integer> retrieveOccupiedCells() {
        List<Integer> occupiedCells = new ArrayList<>();
        ships.forEach(ship -> {
            occupiedCells.addAll(ship.retrieveOccupiedCells());
        });
        return occupiedCells;
    }
}
