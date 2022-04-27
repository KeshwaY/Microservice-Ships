package com.github.ships.ships.users;

import com.github.ships.ships.security.roles.Role;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Document
@RequiredArgsConstructor @Getter @Setter
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull private String email;

    @NonNull private String name;
    private String password;

    private LocalDateTime createdDate;

    @DocumentReference
    private Role role;
}
