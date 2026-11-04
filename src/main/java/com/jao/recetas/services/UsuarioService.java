package com.jao.recetas.services;

import com.jao.recetas.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);

}
