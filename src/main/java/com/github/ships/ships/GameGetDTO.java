package com.github.ships.ships;

public class GameGetDTO {

    private String id;

    public GameGetDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
