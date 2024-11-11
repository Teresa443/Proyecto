package com.example.demo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class nombreYaExiste extends RuntimeException {
    public nombreYaExiste(String message) {
        super(message);
    }
}
