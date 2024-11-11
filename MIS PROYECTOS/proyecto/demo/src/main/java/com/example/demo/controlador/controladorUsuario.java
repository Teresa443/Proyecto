package com.example.demo.controlador;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.excepciones.nombreYaExiste;
import com.example.demo.modelo.Sesion;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.RepositorioSesion;
import com.example.demo.servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping("/subliLinkas")
public class controladorUsuario {
    private final RepositorioSesion repositorioSesion;
    private final ServicioUsuario servicioUsuario;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUserByCorreo(@PathVariable("id") String correo) {
        Usuario usuario = servicioUsuario.obtenerUsuarioPorCorreo(correo);
        return ResponseEntity.ok(usuario);

    }

    @PutMapping("/usuario")
    public ResponseEntity<?> updateUser(@CookieValue("authToken") UUID authToken,
                                        @RequestBody UsuarioDto usuarioDto) {
        Sesion sesion = repositorioSesion.findByToken(authToken);
        Usuario usuario = sesion.getUsuario();
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        try {
            usuario = servicioUsuario.actualizarUsuario(usuarioDto);
            return ResponseEntity.ok(usuario);
        } catch (nombreYaExiste e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @DeleteMapping("/usuario")
    public ResponseEntity<String>  deleteUser(@CookieValue("authToken") UUID authToken){
        Sesion sesion = repositorioSesion.findByToken(authToken);
        Usuario usuario = sesion.getUsuario();
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        servicioUsuario.eliminarUsuario(usuario.getCorreo());
        return ResponseEntity.ok("Usuario eliminado correctamente! ");
    }


}
