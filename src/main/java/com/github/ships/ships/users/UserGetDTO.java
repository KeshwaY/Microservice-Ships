package com.github.ships.ships.users;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserGetDTO {
    @NonNull private String email;
    @NonNull private String name;
}
