package com.github.ships.ships.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Event {
    @NonNull private EventType eventType;
    @NonNull private String message;
}
