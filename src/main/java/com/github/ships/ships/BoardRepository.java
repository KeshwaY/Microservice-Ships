package com.github.ships.ships;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {

    @Query("{ 'gameId' : ?0, 'playerId' : ?1 }")
    List<Board> getSpecifiedBoards(String gameId, int playerId);

}
