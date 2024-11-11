package com.example.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "correo", nullable=false, unique=true)
    private String correo;

    @Column(name = "nombre", nullable=false, unique=true)
    private String nombre;

    @Column(name = "contraseña", nullable=false)
    private String contraseña;

    @Column(name = "dirección", nullable=false)
    private String direccion;

    @Column(name = "telefono", nullable=false)
    private String telefono;
    
}
