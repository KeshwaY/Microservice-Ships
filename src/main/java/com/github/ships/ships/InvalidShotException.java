package com.github.ships.ships;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tinylog.Logger;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidShotException extends RuntimeException {
    public InvalidShotException() {
        super("Shot is not valid!");
        Logger.error("Shot is not valid!");
    }
}
