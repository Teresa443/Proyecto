package com.example.demo.servicio.implementación;

import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.RepositorioProducto;
import com.example.demo.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImplementacionServicioProducto implements ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    @Autowired
    public ImplementacionServicioProducto(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public List<Producto> getAllProducto() {
        return repositorioProducto.findAll();
    }

    @Override
    public Producto getProductoById(Integer productoId) {
        return repositorioProducto.findById(productoId).orElse(null);
    }
}
