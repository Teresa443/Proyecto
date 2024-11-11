package com.example.demo.repositorio;

import com.example.demo.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
    Usuario findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByNombre(String nombre);
}
