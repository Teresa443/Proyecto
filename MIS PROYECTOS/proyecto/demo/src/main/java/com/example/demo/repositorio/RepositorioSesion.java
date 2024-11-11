package com.example.demo.repositorio;

import com.example.demo.modelo.Sesion;
import com.example.demo.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioSesion  extends JpaRepository<Sesion, UUID> {
    List<Sesion> findByUsuario(Usuario usuario);
    Sesion findByToken(UUID uuid);
    Sesion findTopByUsuarioOrderByTimestampDesc(Usuario usuario);
}
