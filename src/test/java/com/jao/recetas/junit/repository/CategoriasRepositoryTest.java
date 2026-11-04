package com.jao.recetas.junit.repository;

import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.repository.CategoriasRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriasRepositoryTest {

    @Autowired
    private CategoriasRepository categoriaRepository;

    @Test
    void findCategoriaByIdTest() {
        Integer id = 2;//Mexicana;

        CategoriaResponse response = this.categoriaRepository.findCategoriaById(id).get();

        assertEquals(id, response.getId());
        assertEquals("Mexicana", response.getNombre());
    }

    @Test
    void findOneByNombreTest() {
        String nombre = "Japonesa";

        CategoriaResponse response = this.categoriaRepository.findOneByNombre(nombre).get();

        assertEquals(3, response.getId());
        assertEquals(nombre, response.getNombre());
    }

    @Test
    void findAllOrderByNombreAscTest() {
        List<CategoriaResponse> categorias = this.categoriaRepository.findAllOrderByNombreAsc();

        assertEquals(9, categorias.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAsc() {
        String infix = "ciana";

        List<CategoriaResponse> categorias = this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(1, categorias.size());
    }


}
