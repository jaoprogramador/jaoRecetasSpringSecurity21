package com.jao.recetas.repository;

import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.model.Categoria;
import com.jao.recetas.model.Recetas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository <Categoria, Integer> {

        @Query("""
            SELECT new com.jao.recetas.dto.CategoriaResponse(c.id, c.nombre, c.procedencia, c.tipoAlimento)
                FROM Categoria c
                WHERE c.id = :id
            """
        )
        Optional<CategoriaResponse> findCategoriaById(@Param("id") Integer id);

        @Query("""
                SELECT new com.jao.recetas.dto.CategoriaResponse(c.id, c.nombre, c.procedencia, c.tipoAlimento)
                    FROM Categoria c
                    WHERE c.nombre = :nombre
                """
        )
        Optional<CategoriaResponse> findOneByNombre(@Param("nombre") String nombre);

        @Query("""
                SELECT new com.jao.recetas.dto.CategoriaResponse(c.id, c.nombre, c.procedencia, c.tipoAlimento)
                    FROM Categoria c
                    ORDER BY c.nombre ASC
                """
        )
        List<CategoriaResponse> findAllOrderByNombreAsc();

        @Query("""
                SELECT new com.jao.recetas.dto.CategoriaResponse(c.id, c.nombre, c.procedencia, c.tipoAlimento)
                    FROM Categoria c
                    WHERE UPPER(c.nombre) LIKE UPPER(CONCAT('%', :infix, '%'))
                    ORDER BY c.nombre ASC
                """
        )
        List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(@Param("infix") String infix);
}