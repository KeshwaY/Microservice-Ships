package com.github.ships.ships.security.authorities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AuthorityDTO {

    @NotEmpty
    @NonNull private String name;

}
