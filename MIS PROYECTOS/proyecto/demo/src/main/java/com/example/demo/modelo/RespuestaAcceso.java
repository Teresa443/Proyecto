package com.example.demo.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class RespuestaAcceso {
    Usuario usuario;
    UUID token;
}
