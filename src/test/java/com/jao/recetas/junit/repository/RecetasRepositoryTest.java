package com.jao.recetas.junit.repository;

import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.repository.RecetasRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles("test")
public class RecetasRepositoryTest {

    @Autowired
    private RecetasRepository recetasRepository;

    @Test
    void findRecetasByIdTest() {
        Integer id = 5;

        RecetaResponse response = this.recetasRepository.findRecetaById(id).get();

        assertEquals(id, response.getId());
        assertEquals(2, response.getCategoriaId());
    }

    @Test
    void findOneByNombreTest() {
        String titulo = "Tacos al Pastor";

        RecetaResponse response = this.recetasRepository.findOneByTitulo(titulo).get();

        assertEquals(titulo, response.getTitulo());
        assertEquals(2, response.getCategoriaId());
    }
    @Test
    void findAllOrderByNombreAscTest() {
        List<RecetaResponse> recetas = this.recetasRepository.findAllOrderByTituloAsc();

        assertEquals(2, recetas.size());
    }

    @Test
    void findByTituloontainingIgnoreCaseOrderByTituloAscTest() {
        String infix = "pastor";

        List<RecetaResponse> recetas = this.recetasRepository.findByNombreContainingIgnoreCaseOrderByTituloAsc(infix);

        assertEquals(1, recetas.size());
    }



}
