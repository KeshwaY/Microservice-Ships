package com.github.ships.ships.websocket;

import lombok.*;

@AllArgsConstructor @RequiredArgsConstructor @Getter @Setter
public class Event {
    private final EventType eventType;
    private final String message;
    private Integer cell;
}
