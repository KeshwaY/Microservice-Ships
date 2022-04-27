package com.github.ships.ships.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor @Getter @Setter
public class UserPostDTO {

    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6)
    private String name;

    @NotEmpty
    private String password;
}
