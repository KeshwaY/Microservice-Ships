package com.github.ships.ships.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Message {
    @NonNull private String content;
}
