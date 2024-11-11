package com.example.demo.controlador;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoProducto;
import com.example.demo.modelo.Factura;
import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.RepositorioCarrito;
import com.example.demo.repositorio.RepositorioProducto;
import com.example.demo.servicio.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
public class ControladorCarrito {

    @Autowired
    private ServicioCarrito servicioCarrito;

    @Autowired
    private RepositorioProducto repositorioProducto;
    @Autowired
    private RepositorioCarrito repositorioCarrito;

    // Crear un nuevo carrito
    @PostMapping("/crear")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito, @RequestParam String email) {
        Carrito nuevoCarrito = servicioCarrito.createCarrito(carrito);
        return ResponseEntity.ok(nuevoCarrito);
    }

    @GetMapping("/user")
    public ResponseEntity<Carrito> getCarritoByCorreo(@RequestParam String correoUsuario) {
        Carrito carrito = servicioCarrito.getCarritoByCorreo(correoUsuario);
        System.out.println(correoUsuario);
        return ResponseEntity.ok(carrito);
    }

    // Obtener todos los productos del carrito por correo de usuario
    @GetMapping("/productos")
    public ResponseEntity<List<CarritoProducto>> obtenerProductosPorCarrito(@RequestParam String correoUsuario) {
        List<CarritoProducto> carritoProductos = servicioCarrito.getAllCarritoProductosByCarrito(correoUsuario);
        System.out.println(carritoProductos.size());
        return ResponseEntity.ok(carritoProductos);
    }

    @PostMapping("/producto")
    public ResponseEntity<String> agregarProductoAlCarrito(@RequestBody Map<String, Object> payload) {
        String correoUsuario = (String) payload.get("correoUsuario");
        Integer productoId = (Integer) payload.get("productoId");
        Integer cantidad = (Integer) payload.get("cantidad");

        // Buscar el producto en el repositorio
        Producto producto = repositorioProducto.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Agregar o actualizar el producto en el carrito usando el servicio
        servicioCarrito.addOrUpdateProductoInCarrito(correoUsuario, producto, cantidad);

        return ResponseEntity.ok("Producto agregado o actualizado en el carrito.");
    }

    // Calcular el precio total del carrito
    @GetMapping("/precio-total")
    public ResponseEntity<Integer> obtenerPrecioTotal(@RequestParam String correoUsuario) {
        int precioTotal = servicioCarrito.getTotalPriceByCarrito(correoUsuario);
        return ResponseEntity.ok(precioTotal);
    }

    @DeleteMapping("/producto")
    public ResponseEntity<String> eliminarProductoDelCarrito(@RequestParam String correoUsuario, @RequestParam Integer productoId) {

        // Buscar el producto en el repositorio
        Producto producto = repositorioProducto.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Llama al servicio para eliminar el producto del carrito
        servicioCarrito.deleteProductoFromCarrito(correoUsuario, producto);

        return ResponseEntity.ok("Producto eliminado del carrito.");
    }
}
