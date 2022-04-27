package com.github.ships.ships;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameCreatedDTO {

    private String id;
    private BoardGetDTO boardGetDTO;

    public GameCreatedDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
