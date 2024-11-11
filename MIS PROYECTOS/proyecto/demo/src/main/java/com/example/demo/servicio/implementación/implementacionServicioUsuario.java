package com.example.demo.servicio.implementación;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.excepciones.correoYaExiste;
import com.example.demo.excepciones.nombreYaExiste;
import com.example.demo.excepciones.recursoNoEncontrado;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.RepositorioUsuario;
import com.example.demo.servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class implementacionServicioUsuario implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Override
    public Usuario crearusuario(UsuarioDto usuarioDto) {
        if (repositorioUsuario.existsByCorreo(usuarioDto.getCorreo())) {
            throw new correoYaExiste("el correo ya se está usando");
        }
        if (repositorioUsuario.existsByNombre(usuarioDto.getNombre())) {
            throw new nombreYaExiste("Nombre de usuario ya en uso");
        }
        // Encriptar la contraseña
        String contraseñaEncriptada = encriptarContraseña(usuarioDto.getContraseña());
        usuarioDto.setContraseña(contraseñaEncriptada);
        Usuario usuario = UsuarioMapper.mapToUsuario(usuarioDto);
        return repositorioUsuario.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = repositorioUsuario.findByCorreo(correo);
        if (usuario == null) {
            throw new recursoNoEncontrado("No existe un usuario con el id " + correo);
        }
        return usuario;
    }

    @Override
    public Usuario actualizarUsuario(UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public void eliminarUsuario(String id) {

    }

    private String encriptarContraseña(String contraseña) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(contraseña.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}
