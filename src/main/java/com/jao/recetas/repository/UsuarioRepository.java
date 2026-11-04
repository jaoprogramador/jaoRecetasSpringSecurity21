package com.jao.recetas.repository;

import com.jao.recetas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository  <Usuario, Integer> {
    Optional<Usuario> findOneByUsername(String username);
}
