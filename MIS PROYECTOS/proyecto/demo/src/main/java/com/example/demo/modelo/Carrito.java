package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "carrito")
public class Carrito {
    @Id
    @Column(name = "carrito_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carritoId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "correo", referencedColumnName = "correo", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "correo")
    @NonNull
    private String correo;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Maneja la relación con CarritoProducto
    private List<CarritoProducto> productos = new ArrayList<>();

    @Column(name = "precio_total")
    private int precioTotal;
}
