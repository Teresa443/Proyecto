package com.example.demo.repositorio;

import com.example.demo.modelo.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioCarrito extends JpaRepository<Carrito, Integer> {
    Carrito findByCorreo(String correo);
    Optional<Carrito> findById(Integer id);
}
