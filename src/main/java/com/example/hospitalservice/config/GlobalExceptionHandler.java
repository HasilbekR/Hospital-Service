package com.example.hospitalservice.config;

import com.example.hospitalservice.exceptions.DataNotFoundException;
import com.example.hospitalservice.exceptions.UserBadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFoundExceptionHandler(
            DataNotFoundException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = {UserBadRequestException.class})
    public ResponseEntity<String> userBadRequestExceptionHandler(
            UserBadRequestException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
