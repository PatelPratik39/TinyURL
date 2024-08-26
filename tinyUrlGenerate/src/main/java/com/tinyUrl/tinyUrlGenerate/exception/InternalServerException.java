package com.tinyUrl.tinyUrlGenerate.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException ( String message ) {
        super(message);
    }
}
