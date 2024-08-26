package com.tinyUrl.tinyUrlGenerate.exception;

public class UrlNotFoundException  extends RuntimeException{
    public UrlNotFoundException ( String message ) {
        super(message);
    }
}
