package com.github.ships.ships;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Document
public class Game {

    @Id
    private String id;

    @DocumentReference(lookup = "{'gameId':?#{#self._id} }")
    private Collection<Board> boards;

    public Game() {
    }

    public Game(String id, Collection<Board> boards) {
        this.id = id;
        this.boards = boards;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Collection<Board> getBoards() {
        return boards;
    }

    public void setBoards(final Collection<Board> boards) {
        this.boards = boards;
    }
}
