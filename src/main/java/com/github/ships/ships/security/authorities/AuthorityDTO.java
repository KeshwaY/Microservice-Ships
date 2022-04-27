package com.github.ships.ships.security.authorities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthorityDTO {

    @NotEmpty
    private String name;

}
