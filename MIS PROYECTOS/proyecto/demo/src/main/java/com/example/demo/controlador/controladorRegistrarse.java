package com.example.demo.controlador;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.excepciones.correoYaExiste;
import com.example.demo.excepciones.nombreYaExiste;
import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ServicioCarrito;
import com.example.demo.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class controladorRegistrarse {
    private final ServicioUsuario servicioUsuario;// Inyecta el servicio de carrito

    @Autowired
    public controladorRegistrarse(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping("/IniciarSesion")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDto usuarioDto) {
        // Verificar si la contraseña no está vacía
        if (usuarioDto.getContraseña() == null || usuarioDto.getContraseña().isEmpty()) {
            return new ResponseEntity<>("La contraseña no puede ser vacia", HttpStatus.BAD_REQUEST);
        }

        try {
            // Intentar crear el nuevo usuario
            Usuario usuarioCreado = servicioUsuario.crearusuario(usuarioDto);

            return ResponseEntity.ok(usuarioCreado);
        } catch (correoYaExiste e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (nombreYaExiste e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}