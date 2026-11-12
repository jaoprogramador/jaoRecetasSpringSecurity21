package com.jao.recetas.services.security;

import com.jao.recetas.dto.UsuarioRequest;
import com.jao.recetas.model.Rol;
import com.jao.recetas.model.Usuario;

import java.util.Optional;

import com.jao.recetas.repository.RolRepository;
import com.jao.recetas.repository.UsuarioRepository;
import com.jao.recetas.services.SendEmailService;
import com.jao.recetas.services.security.data.NewPasswordInfo;
import com.jao.recetas.util.exception.DatabaseException;
import com.jao.recetas.util.exception.InvalidOperationException;
import com.jao.recetas.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor @Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailService sendEmailService;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public void createUser(UsuarioRequest request) {
        log.info("UsuarioServiceImpl:::createUser",request);
        Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(request.getUsername());
        log.info("UsuarioServiceImpl:::createUser usuarioBD",request.getUsername());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con el mismo nombre de usuario");
        }
        log.info("UsuarioServiceImpl:::createUser",request);
        usuarioBD = this.usuarioRepository.findOneByEmail(request.getEmail());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con la misma dirección de correo");
        }

        try {
            Optional<Rol> rolBD = this.rolRepository.findOneByNombre("USER");
            log.info("UsuarioServiceImpl:::createUser::rolBD",rolBD);
            if (rolBD.isEmpty()) {
                throw new InvalidOperationException("Error de configuración. No existe el Rol USER.");
            }
            log.info("UsuarioServiceImpl:::createUser::request.getPassword()",request.getPassword());
            String password = this.passwordEncoder.encode(request.getPassword());
            Usuario usuario = new Usuario(request, password, rolBD.get());
            log.info("UsuarioServiceImpl:::createUser::rolBD.get()",rolBD.get());
            this.usuarioRepository.save(usuario);
        } catch (InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            String message = String.format("Error de base de datos creando el usuario: %s", request.getUsername());
            throw new DatabaseException(message, e);
        }

        String texto = String.format("""
                Hola %s,
                
                Su usuario ha sido creado con las siguientes credenciales:
                Nombre de usuario: %s
                Contraseña: %s
                
                Por seguridad, debe cambiar su contraseña a la brevedad posible.
                """, request.getNombre(), request.getUsername(), request.getPassword());

        this.sendEmailService.sendEmail(request.getEmail(), "Su usuario ha sido creado", texto);
    }

    @Override
    @Transactional
    public void blockUser(String username) {
        Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(username);
        log.info("UsuarioServiceImpl:::blockUser::username",username);
        if (usuarioBD.isEmpty()) {
            throw new ResourceNotFoundException("No se puede bloquear el usuario porque no existe");
        }

        try {
            Usuario usuario = usuarioBD.get();
            usuario.setActivo(false);
            log.info("UsuarioServiceImpl:::blockUser::usuario",usuario);
            this.usuarioRepository.save(usuario);
        } catch (Exception e) {
            String message = String.format("Error de base de datos bloqueando el usuario: %s", username);
            log.error("UsuarioServiceImpl:::blockUser::error",e.getMessage());
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public void changePassword(NewPasswordInfo info) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(username);
            if (usuarioBD.isEmpty()) {
                throw new ResourceNotFoundException("No se ha podido recuperar los datos del usuario " + username);
            }

            Usuario usuario = usuarioBD.get();
            if (!this.passwordEncoder.matches(info.getPassword(), usuario.getPassword())) {
                throw new InvalidOperationException("El password actual no es correcto");
            }

            String password = this.passwordEncoder.encode(info.getNewPassword());
            usuario.setPassword(password);
            this.usuarioRepository.save(usuario);
        } catch (ResourceNotFoundException | InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            String message = String.format("Error de base de datos cambiando la contraseña del usuario: %s", username);
            throw new DatabaseException(message, e);
        }
    }

}
