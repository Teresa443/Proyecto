package com.example.demo.modelo;

import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "total_a_pagar")
    private int totalAPagar;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "direccion_usuario")
    private String direccionUsuario;
}
