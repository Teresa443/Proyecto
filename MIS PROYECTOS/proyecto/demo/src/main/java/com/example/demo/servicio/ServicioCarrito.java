package com.example.demo.servicio;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoProducto;
import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ServicioCarrito {
    Carrito createCarrito(Carrito carrito);
    Carrito getCarritoByCorreo(String correoUsuario);
    List<CarritoProducto> getAllCarritoProductosByCarrito(String correoUsuario);
    void addOrUpdateProductoInCarrito(String correoUsuario, Producto producto, int cantidad);
    int getTotalPriceByCarrito(String correoUsuario);
    void deleteProductoFromCarrito(String correoUsuario, Producto producto);
    void vaciarCarrito(Integer carritoId);
}
