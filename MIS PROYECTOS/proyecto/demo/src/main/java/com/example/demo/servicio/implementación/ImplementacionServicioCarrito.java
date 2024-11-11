package com.example.demo.servicio.implementación;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoProducto;
import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.RepositorioCarrito;
import com.example.demo.repositorio.RepositorioProducto;
import com.example.demo.servicio.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplementacionServicioCarrito implements ServicioCarrito {

    @Autowired
    private RepositorioCarrito repositorioCarrito;

    @Autowired
    private RepositorioProducto repositorioProducto;

    @Override
    public Carrito createCarrito(Carrito carrito) {
        return repositorioCarrito.save(carrito);
    }

    @Override
    public List<CarritoProducto> getAllCarritoProductosByCarrito(String correoUsuario) {
        Carrito carrito = repositorioCarrito.findByCorreo(correoUsuario);
        return carrito != null ? carrito.getProductos() : null; // Manejo de null si no se encuentra el carrito
    }

    @Override
    public Carrito getCarritoByCorreo(String correoUsuario) {
        Carrito carrito = repositorioCarrito.findByCorreo(correoUsuario);
        return carrito != null ? carrito : null; // Manejo de null si no se encuentra el carrito
    }

    @Override
    public void addOrUpdateProductoInCarrito(String correoUsuario, Producto producto, int cantidad) {
        Carrito carrito = repositorioCarrito.findByCorreo(correoUsuario);
        if (carrito != null) {
            Optional<CarritoProducto> carritoProductoOpt = carrito.getProductos().stream()
                    .filter(cp -> cp.getProductoId().equals(producto.getId())) // Cambiado para usar productoId
                    .findFirst();

            CarritoProducto carritoProducto;

            if (carritoProductoOpt.isPresent()) {
                // El producto ya está en el carrito, actualiza la cantidad
                carritoProducto = carritoProductoOpt.get();
                carritoProducto.setCantidad(carritoProducto.getCantidad() + cantidad);
            } else {
                // El producto no está en el carrito, agrega un nuevo CarritoProducto
                carritoProducto = new CarritoProducto(carrito, producto.getId(), cantidad); // Cambiado para usar productoId
                carrito.getProductos().add(carritoProducto);
            }
            int NuevoPrecioTotal = calcularPrecioTotal(carrito);
            carrito.setPrecioTotal(NuevoPrecioTotal);
            repositorioCarrito.save(carrito); // Guardar el carrito actualizado
        } else {
            System.out.println("El carrito no fue encontrado para el usuario con correo: " + correoUsuario);
        }
    }

    @Override
    public int getTotalPriceByCarrito(String correoUsuario) {
        Carrito carrito = repositorioCarrito.findByCorreo(correoUsuario);
        return calcularPrecioTotal(carrito);
    }

    @Override
    public void deleteProductoFromCarrito(String correoUsuario, Producto producto) {
        Carrito carrito = repositorioCarrito.findByCorreo(correoUsuario);
        if (carrito != null) {

            carrito.getProductos().removeIf(cp -> cp.getProductoId().equals(producto.getId())); // Cambiado para usar productoId
            repositorioCarrito.save(carrito); // Guardar el carrito actualizado
        } else {
            System.out.println("El carrito no fue encontrado para el usuario con correo: " + correoUsuario);
        }
    }

    @Override
    public void vaciarCarrito(Integer carritoId) {
        Carrito carrito = repositorioCarrito.findById(carritoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        carrito.getProductos().clear();
        carrito.setPrecioTotal(0);
        repositorioCarrito.save(carrito);
    }


    private int calcularPrecioTotal(Carrito carrito){
        if (carrito != null) {
            int precioTotal = 0;
            for (CarritoProducto cp : carrito.getProductos()) {
                Producto producto = repositorioProducto.findById(cp.getProductoId()) // Obtener el producto por ID
                        .orElse(null);
                if (producto != null) {
                    precioTotal += Integer.valueOf(producto.getPrecio()) * cp.getCantidad();
                }
            }
            return precioTotal;
        }
        return 0;
    }

}
