package com.akil.reddit.backend.controller;

import com.akil.reddit.backend.exception.SpringRedditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleMyCustomException(SpringRedditException e) {
        log.error("error occurred {}", e);
        return new ResponseEntity<>("Something happened: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}