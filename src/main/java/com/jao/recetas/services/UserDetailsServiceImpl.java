package com.jao.recetas.services;

import com.jao.recetas.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Consulta usuario...",username);
        log.info("==============================");
        Optional<Usuario> usuarioDB = this.usuarioService.findByUsername(username);
        if (usuarioDB.isEmpty()) {
            throw new UsernameNotFoundException("No existe el usuario " + username);
        }
        log.info("Cargando los usuarios de BD...");
        log.info("==============================");
        Usuario usuario = usuarioDB.get();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getRoles()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getNombre()))));
        log.info("Cargando los roles de BD...");
        log.info("==============================");
        usuario.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombre())));
        log.info("Cargando los permisos de BD...");
        log.info("==============================");
        return User.withUsername(username)
                .password(usuario.getPassword())
                .disabled(!usuario.isActivo())
                .authorities(authorityList)
                .build();
    }
}