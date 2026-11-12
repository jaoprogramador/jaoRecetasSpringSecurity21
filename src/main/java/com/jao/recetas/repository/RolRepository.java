package com.jao.recetas.repository;

import com.jao.recetas.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository <Rol, Integer> {
    Optional<Rol> findOneByNombre(String nombre);

}
