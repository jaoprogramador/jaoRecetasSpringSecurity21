package com.jao.recetas.services.security;

import com.jao.recetas.model.Usuario;
import com.jao.recetas.security.data.LoginInfo;
import com.jao.recetas.security.data.TokenInfo;
import com.jao.recetas.util.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilsService jwtUtilsService;

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
        log.info("=== DEBUG USUARIO ===");
        log.info("Username cargado: {}", usuario.getUsername());
        usuario.getRoles().forEach(rol -> log.info("Rol en entidad: {}", rol.getNombre()));
        log.info("=====================");

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

    public TokenInfo login(LoginInfo loginInfo) throws UsernameNotFoundException, InvalidUserException {
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();

        UserDetails userDetails = this.loadUserByUsername(username);
        Optional<Usuario> usuarioDB = this.usuarioService.findByUsername(username);
        log.info("Cargando los usuarios de BD...");
        log.info("==============================");
        Usuario usuario = usuarioDB.get();
        log.info("=== DEBUG USUARIO ===");
        log.info("Username cargado: {}", usuario.getUsername());
        usuario.getRoles().forEach(rol -> log.info("Rol en entidad: {}", rol.getNombre()));
        log.info("=====================");

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

        if (!userDetails.isEnabled()) {
            throw new InvalidUserException("El usuario está deshabilitado");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidUserException("Las credenciales suministradas son incorrectas");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenInfo(this.jwtUtilsService.createToken(authentication));
    }
}