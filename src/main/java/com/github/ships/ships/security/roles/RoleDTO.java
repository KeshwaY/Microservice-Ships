package com.github.ships.ships.security.roles;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@NoArgsConstructor
public class RoleDTO {

    @NotEmpty
    @NonNull private String name;

    @NotEmpty
    @NonNull private Integer grade;

    @NonNull private Collection<String> authorities;
}
