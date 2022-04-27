package com.github.ships.ships;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@RequiredArgsConstructor @Getter @Setter
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull private String email;

    @NonNull private String name;
    @NonNull private String password;

    private LocalDateTime createdDate;
}
