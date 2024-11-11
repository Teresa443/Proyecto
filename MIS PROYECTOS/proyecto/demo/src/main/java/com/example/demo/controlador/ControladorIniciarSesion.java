package com.example.demo.controlador;
import com.example.demo.modelo.*;
import com.example.demo.repositorio.RepositorioSesion;
import com.example.demo.repositorio.RepositorioUsuario;
import com.example.demo.servicio.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import java.time.Instant;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ControladorIniciarSesion {
    private final RepositorioSesion repositorioSesion;
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioCarrito servicioCarrito; // Agregar servicio de carrito

    @Value("${is.prod}")
    private String isProd;

    @Autowired
    public ControladorIniciarSesion(RepositorioSesion repositorioSesion,
                                    RepositorioUsuario repositorioUsuario,
                                    ServicioCarrito servicioCarrito) { // Inyección del servicio de carrito
        this.repositorioSesion = repositorioSesion;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioCarrito = servicioCarrito; // Asignar el servicio de carrito
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaAcceso> loginSubmit(
            @RequestBody PeticionAcceso loginRequest,
            HttpServletResponse response) {
        Usuario usuario = repositorioUsuario.findByCorreo(loginRequest.getCorreo());
        System.out.println("user" + usuario);
        if (usuario == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if (!usuario.getContraseña().equals(encryptPassword(loginRequest.getContraseña()))) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            UUID token = UUID.randomUUID();
            Sesion sesion = new Sesion(token, Instant.now(), usuario);
            repositorioSesion.save(sesion);
            // Crear carrito si no existe
            if (servicioCarrito.getAllCarritoProductosByCarrito(usuario.getCorreo()) == null) {
                Carrito nuevoCarrito = new Carrito();
                nuevoCarrito.setCorreo(usuario.getCorreo());
                servicioCarrito.createCarrito(nuevoCarrito);
            }
            // Poner en la respuesta, la cookie que deseo que el browser ponga en mi dominio de backend
            ResponseCookie springCookie = null;
            if (isProd.equals("true")) {
                springCookie = ResponseCookie.from("authToken", token.toString())
                        .path("/")
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        //.domain("wardrobewhizapi.azurewebsites.net")
                        .build();
            } else {
                springCookie = ResponseCookie.from("authToken", token.toString())
                        .path("/")
                        .httpOnly(true)
                        .sameSite("Strict")
                        .secure(false)
                        .domain("localhost")
                        .build();
            }
            System.out.println("springCookie: " + springCookie);

            response.addHeader("Set-Cookie", springCookie.toString());

            return new ResponseEntity<>(new RespuestaAcceso(usuario, token), HttpStatus.OK);
        }
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}