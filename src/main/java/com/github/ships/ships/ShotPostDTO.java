package com.github.ships.ships;

/**
 * @author Anna Ovcharenko
 */
public class ShotPostDTO {
    private String gameId;
    private int cellId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }
}
