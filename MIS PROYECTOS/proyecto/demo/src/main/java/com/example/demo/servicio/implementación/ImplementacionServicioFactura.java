package com.example.demo.servicio.implementación;


import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Factura;
import com.example.demo.repositorio.RepositorioFactura;
import com.example.demo.servicio.ServicioCarrito;
import com.example.demo.servicio.ServicioFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ImplementacionServicioFactura implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;
    private final ServicioCarrito servicioCarrito;

    @Autowired
    public ImplementacionServicioFactura(RepositorioFactura repositorioFactura, ServicioCarrito servicioCarrito) {
        this.repositorioFactura = repositorioFactura;
        this.servicioCarrito = servicioCarrito;
    }

    public Factura crearFactura(Carrito carrito) {

        Factura factura = new Factura();
        factura.setCarrito(carrito);
        factura.setFechaCreacion(LocalDate.now());
        factura.setTotalAPagar(carrito.getPrecioTotal()); // Asumiendo que el carrito ya tiene el precio total calculado
        factura.setNombreUsuario(carrito.getUsuario().getNombre());
        factura.setDireccionUsuario(carrito.getUsuario().getDireccion());

        servicioCarrito.vaciarCarrito(carrito.getCarritoId());

        return repositorioFactura.save(factura);
    }

    public Factura obtenerFacturaPorId(Integer id) {
        return repositorioFactura.findById(id).orElse(null); // Devuelve null si no se encuentra
    }
}
