package com.example.demo.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "producto")
public class Producto {
    @Id
    @Column(name = "producto_id", nullable=false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable=false)
    private String nombre;

    @Column(name = "precio", nullable=false)
    private String precio;

    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private String imagen;

    public Producto(String nombre, String precio, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }
}
