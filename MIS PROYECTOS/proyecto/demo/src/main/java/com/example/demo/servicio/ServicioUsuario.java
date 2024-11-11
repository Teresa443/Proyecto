package com.example.demo.servicio;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.modelo.Usuario;

public interface ServicioUsuario {
    Usuario crearusuario(UsuarioDto usuarioDto);
    Usuario obtenerUsuarioPorCorreo(String correo);
    Usuario actualizarUsuario(UsuarioDto usuarioDto);
    void eliminarUsuario(String id);
}
