package com.tinyUrl.tinyUrlGenerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFoundException(UrlNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException( ResourceNotFoundException resourceNotFoundException, WebRequest request ){
        Map<String,Object> body = new HashMap <>();

        body.put("timeStamp" ,LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", resourceNotFoundException.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(resourceNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( BadRequestException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequestException(BadRequestException badRequestException, WebRequest request){

        Map<String,Object> body = new HashMap<>();

        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", badRequestException.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( ConflictException.class)
    public ResponseEntity<Map<String ,Object>> handleConflictException(ConflictException conflictException, WebRequest request){

        Map<String, Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error","Conflict");
        body.put("message",conflictException.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity <>(body,HttpStatus.CONFLICT);
    }
    @ExceptionHandler( InternalServerException.class)
    public ResponseEntity<Map<String,Object>> handleInternalServerException(InternalServerException internalServerException, WebRequest request){

        Map<String, Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error","Internal Server Error");
        body.put("message", internalServerException.getMessage());
        body.put("path",request.getDescription(false).substring(4));

        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
