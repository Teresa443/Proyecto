package com.example.demo.controlador;

import com.example.demo.modelo.Sesion;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.RepositorioSesion;
import com.example.demo.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/session")
public class controladorSesion {
    @Autowired
    private RepositorioSesion repositorioSesion;

    @Autowired
    private RepositorioUsuario repositorioUsuario; // Asumiendo que tienes un repositorio de usuarios

    @GetMapping("/token")
    public ResponseEntity<String> getTokenForSession(@RequestParam String correo) {
        Usuario usuario = repositorioUsuario.findByCorreo(correo);
        if (usuario != null) {
            Sesion sesion = repositorioSesion.findTopByUsuarioOrderByTimestampDesc(usuario);
            System.out.println(sesion);
            if (sesion != null) {
                return ResponseEntity.ok(sesion.getToken().toString());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<String> getUsuarioEmailFromSession(@RequestParam UUID sessionId) {
        Sesion sesion = repositorioSesion.findByToken(sessionId);
        if (sesion != null) {
            String correoUsuario = sesion.getUsuario().getCorreo();
            return ResponseEntity.ok(correoUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/usuario")
    public ResponseEntity<String> deleteSession(@CookieValue("authToken") UUID authToken) {
        Sesion sesion = repositorioSesion.findByToken(authToken);
        if (sesion == null) {
            return ResponseEntity.notFound().build();
        }
        repositorioSesion.deleteById(authToken);
        return ResponseEntity.ok("Sesion eliminada correctamente! ");
    }
}
