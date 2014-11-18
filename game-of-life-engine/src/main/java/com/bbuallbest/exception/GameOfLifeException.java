package com.bbuallbest.exception;

/**
 * Created by happy on 16/11/2014.
 */
public class GameOfLifeException extends RuntimeException{

    public GameOfLifeException() {}

    public GameOfLifeException(String message) {
        super(message);
    }
}
