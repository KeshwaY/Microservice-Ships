package com.github.ships.ships.security.roles;

import com.github.ships.ships.security.authorities.Authority;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Document
@RequiredArgsConstructor @Getter @Setter
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull private String name;

    @NonNull private Integer grade;

    @DocumentReference
    private Collection<Authority> authorities;
}
