package com.example.demo.servicio;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Factura;

public interface ServicioFactura {
    Factura crearFactura(Carrito carrito);
    Factura obtenerFacturaPorId(Integer id);
}

