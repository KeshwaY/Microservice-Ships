package com.github.ships.ships;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tinylog.Logger;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotUserTurnException extends RuntimeException {
    public NotUserTurnException() {
        super("It is opponent's turn!");
        Logger.error("It is opponent's turn!");
    }
}
