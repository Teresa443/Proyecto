package com.example.demo.repositorio;

import com.example.demo.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RepositorioProducto extends JpaRepository<Producto, Integer> {
}