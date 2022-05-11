package com.github.ships.ships;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tinylog.Logger;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBoardSizeException extends RuntimeException{
    public InvalidBoardSizeException() {
        super("Invalid board size. Should be in range [10, 15].");
        Logger.error("Invalid board size. Should be in range [10, 15].");
    }
}
