package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto {
    private String nombre;
    private String correo;
    private String contraseña;
    private String direccion;
    private String telefono;
}
