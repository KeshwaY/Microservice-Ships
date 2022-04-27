package com.github.ships.ships;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class Board {

    @Id
    private String id;

    private String gameId;
    private int playerId;

    private int width;
    private int height;
    private final Map<Integer, Cell> cells;

    public Board(final String gameId, final int playerId, final int width, final int height, final Map<Integer, Cell> cells) {
        this.playerId = playerId;
        this.id = id;
        this.gameId = gameId;
        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(final String gameId) {
        this.gameId = gameId;
    }

    public int getWidth() {
        return width;
    }

    public Map<Integer, Cell> getCells() {
        return cells;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(final int playerId) {
        this.playerId = playerId;
    }
}
