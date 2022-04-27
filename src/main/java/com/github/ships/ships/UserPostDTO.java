package com.github.ships.ships;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor @Getter @Setter
public class UserPostDTO {

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;
}
