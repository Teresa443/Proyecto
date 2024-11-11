package com.example.demo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class recursoNoEncontrado extends RuntimeException{
    public recursoNoEncontrado(String message){
        super(message);
    }
}