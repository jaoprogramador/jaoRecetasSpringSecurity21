package com.jao.recetas.services.security;

import com.jao.recetas.dto.UsuarioRequest;
import com.jao.recetas.model.Usuario;
import com.jao.recetas.services.security.data.NewPasswordInfo;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
    void createUser(UsuarioRequest request);
    void blockUser(String username);
    void changePassword(NewPasswordInfo info);

}
