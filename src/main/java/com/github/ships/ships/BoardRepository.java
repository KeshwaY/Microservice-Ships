package com.github.ships.ships;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {

    @Query("{ 'gameId' : ?0, 'playerId' : ?1 }")
    List<Board> getSpecifiedBoards(String gameId, int playerId);

    Optional<Board> getBoardByGameIdAndPlayerId(String gameId, int playerId);
}
