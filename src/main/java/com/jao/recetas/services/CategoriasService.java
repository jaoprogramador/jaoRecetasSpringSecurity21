package com.jao.recetas.services;

import com.jao.recetas.dto.CategoriaRequest;
import com.jao.recetas.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriasService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findOneByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc( String infix);

    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse updateCategoria(Integer id, CategoriaRequest request);
    boolean deleteCategoria(Integer id);

}
