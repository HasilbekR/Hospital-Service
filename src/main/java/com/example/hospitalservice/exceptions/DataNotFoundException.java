package com.example.hospitalservice.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String massage){
        super(massage);
    }
}
