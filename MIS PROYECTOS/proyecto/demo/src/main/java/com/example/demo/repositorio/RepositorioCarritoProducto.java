package com.example.demo.repositorio;

import com.example.demo.modelo.CarritoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCarritoProducto extends JpaRepository<CarritoProducto, Long> {
    //List<CarritoProducto> findByCarrito(String correoUsuario);
}

