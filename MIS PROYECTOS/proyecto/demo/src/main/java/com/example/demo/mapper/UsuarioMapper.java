package com.example.demo.mapper;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.modelo.Usuario;

public class UsuarioMapper {
    public static Usuario mapToUsuario(UsuarioDto usuarioDto){
        return new Usuario(
                usuarioDto.getCorreo(),
                usuarioDto.getNombre(),
                usuarioDto.getContraseña(),
                usuarioDto.getDireccion(),
                usuarioDto.getTelefono()
        );
    }
}
