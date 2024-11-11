package com.example.demo.controlador;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Controller
@RequestMapping("/api")
public class ControladorProducto {

    private final ServicioProducto servicioProducto;

    @Autowired
    public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = servicioProducto.getAllProducto();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<Producto> getProductoPorId(@PathVariable Integer id) {
        Producto producto = servicioProducto.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(producto);
    }
}
