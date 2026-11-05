package com.jao.recetas.services.security;

import com.jao.recetas.model.Usuario;

import java.util.Optional;

import com.jao.recetas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findOneByUsername(username);
    }
}
