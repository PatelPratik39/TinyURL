package com.tinyUrl.tinyUrlGenerate.exception;

public class ConflictException extends RuntimeException {

    public ConflictException ( String message ) {
        super(message);
    }
}
