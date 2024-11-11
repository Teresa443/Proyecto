package com.example.demo.repositorio;

import com.example.demo.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioFactura extends JpaRepository<Factura, Integer> {
    // Puedes agregar métodos adicionales si es necesario, como buscar facturas por usuario
}

