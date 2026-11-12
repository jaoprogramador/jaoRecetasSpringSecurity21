package com.jao.recetas.util;

import com.jao.recetas.model.Permiso;
import com.jao.recetas.model.Rol;
import com.jao.recetas.model.Usuario;
import com.jao.recetas.repository.PermisoRepository;
import com.jao.recetas.repository.RolRepository;
import com.jao.recetas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class InicializacionRunner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Inicializando la aplicación...");

        if (this.permisoRepository.count() == 0) {
            log.info("Creando los permisos...");
            Permiso read = this.permisoRepository.save(new Permiso("READ"));
            Permiso write = this.permisoRepository.save(new Permiso("WRITE"));
            Permiso delete = this.permisoRepository.save(new Permiso("DELETE"));

            log.info("Creando los roles...");
            Rol user = this.rolRepository.save(new Rol("USER", Set.of(read, write)));
            Rol admin = this.rolRepository.save(new Rol("ADMIN", Set.of(read, write, delete)));
            Rol guest = this.rolRepository.save(new Rol("GUEST", Set.of(read)));

            log.info("Creando los usuarios...");

            Usuario rafa = new Usuario("rafa", this.passwordEncoder.encode("1234"), "rafa@mail.com", "Rafa", Set.of(admin));
            Usuario pedro = new Usuario("pedro", this.passwordEncoder.encode("1234"), "pedro@mail.com", "Pedro", Set.of(user));
            Usuario juan = new Usuario("juan", this.passwordEncoder.encode("1234"), "juan@mail.com", "Juan", Set.of(guest));

            this.usuarioRepository.saveAll(List.of(rafa, pedro, juan)); // O guardarlos uno a uno como tenías

            log.info("¡Datos iniciales cargados con éxito!");
        } else {
            log.info("La base de datos ya contiene datos. Omitiendo inicialización.");
        }
    }
}
