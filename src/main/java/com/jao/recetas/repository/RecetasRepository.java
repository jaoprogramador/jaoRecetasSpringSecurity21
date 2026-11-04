package com.jao.recetas.repository;

import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.model.Recetas;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RecetasRepository extends JpaRepository <Recetas, Integer> {
    @Query(
            """
                    SELECT new com.jao.recetas.dto.RecetaResponse(r.id, r.categoriaId, r.titulo, r.descripcion, r.tiempoPreparacionMinutos,  r.porciones)
                        FROM Recetas r
                        WHERE r.id = :id
                    """
    )
    Optional<RecetaResponse> findRecetaById(@Param("id") Integer id);

    @Query(
            """
                    SELECT new com.jao.recetas.dto.RecetaResponse(r.id, r.categoriaId, r.titulo, r.descripcion, r.tiempoPreparacionMinutos,  r.porciones)
                        FROM Recetas r
                        WHERE r.titulo = :titulo
                    """
    )
    Optional<RecetaResponse> findOneByTitulo(@Param("titulo") String titulo);

    @Query(
            """
                    SELECT new com.jao.recetas.dto.RecetaResponse(r.id, r.categoriaId, r.titulo, r.descripcion, r.tiempoPreparacionMinutos,  r.porciones)
                        FROM Recetas r
                        ORDER BY r.titulo ASC
                    """
    )
    List<RecetaResponse> findAllOrderByTituloAsc();

    @Query(
            """
                    SELECT new com.jao.recetas.dto.RecetaResponse( r.id, r.categoriaId, r.titulo, r.descripcion, r.tiempoPreparacionMinutos,  r.porciones)
                        FROM Recetas r
                        WHERE UPPER(r.titulo) LIKE UPPER(CONCAT('%', :infix, '%'))
                        ORDER BY r.titulo ASC
                    """
    )
    List<RecetaResponse> findByNombreContainingIgnoreCaseOrderByTituloAsc(@Param("infix") String infix);

    @Query(
            """
                    SELECT new com.jao.recetas.dto.RecetaResponse(r.id, r.categoriaId, r.titulo, r.descripcion, r.tiempoPreparacionMinutos,  r.porciones)
                        FROM Recetas r
                        WHERE r.categoriaId = :categoriaId
                        ORDER BY r.titulo ASC
                    """
    )
    List<RecetaResponse> findByCategoriaIdOrderByTituloAsc(@Param("categoriaId") Integer categoriaId);

    long countByCategoriaId(Integer categoriaId);




}
