package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "carrito_producto")
public class CarritoProducto {

    @Id
    @Column(name = "carrito_producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carritoProductoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    @JsonBackReference // Evita la serialización recursiva
    private Carrito carrito;

    @Column(name = "producto_id")  // Almacena solo el ID del producto
    private Integer productoId;

    @Column(name = "cantidad")
    private Integer cantidad;

    public CarritoProducto(Carrito carrito, Integer productoId, int cantidad) {
        this.carrito = carrito;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}

