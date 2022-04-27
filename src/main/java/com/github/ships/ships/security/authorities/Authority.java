package com.github.ships.ships.security.authorities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor @Getter @Setter
public class Authority {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull private String name;
}
