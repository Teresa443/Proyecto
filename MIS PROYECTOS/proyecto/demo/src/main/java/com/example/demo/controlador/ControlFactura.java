package com.example.demo.controlador;

import com.example.demo.modelo.Factura;
import com.example.demo.modelo.Carrito;
import com.example.demo.servicio.ServicioFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factura")
public class ControlFactura {

    private final ServicioFactura servicioFactura;

    @Autowired
    public ControlFactura(ServicioFactura servicioFactura) {
        this.servicioFactura = servicioFactura;
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Carrito carrito) {
        Factura nuevaFactura = servicioFactura.crearFactura(carrito);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

}

