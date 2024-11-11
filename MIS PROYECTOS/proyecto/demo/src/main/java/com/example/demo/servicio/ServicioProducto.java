package com.example.demo.servicio;

import com.example.demo.modelo.Producto;

import java.util.List;
import java.util.UUID;

public interface ServicioProducto {
    //Producto createProducto(ClothingDto clothingDto, String email);
    List<Producto> getAllProducto();
    Producto getProductoById(Integer prendasId);
    //Producto updateClothing(UUID prendaId);
    //void deletePrenda(UUID prendaId);
}
