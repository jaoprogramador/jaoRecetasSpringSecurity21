package com.jao.recetas.junit.service;

import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.repository.CategoriasRepository;
import com.jao.recetas.repository.RecetasRepository;
import com.jao.recetas.services.CategoriasServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class RecetasServiceTest {
    @Mock
    private CategoriasRepository categoriaRepository;

    @Mock
    private RecetasRepository recetasRepository;

    @InjectMocks
    private CategoriasServiceImpl categoriaService;

    @Test
    void testFindCategoriaById_CategoriaExists() {
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Italiana");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("Italiana", categoria.get().getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
    }
    @Test
    void testFindCategoriaById_CategoriaNotFound() {
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaById(1);
    }

    void testFindOneByTitulo_RecetaExists() {
        RecetaResponse recetaoDB = new RecetaResponse(3,  "Sushi Roll California");
        when(this.recetasRepository.findOneByTitulo("Sushi Roll California")).thenReturn(Optional.of(recetaoDB));

        Optional<RecetaResponse> producto = this.recetasRepository.findOneByTitulo("Sushi Roll California");

        assertTrue(producto.isPresent());
        assertEquals(3, producto.get().getId());
        verify(this.recetasRepository).findOneByTitulo("Sushi Roll California");
    }

    @Test
    void testFindOneByTitulo_RecetaNotFount() {
        when(this.recetasRepository.findOneByTitulo("Sushi Roll California")).thenReturn(Optional.empty());

        Optional<RecetaResponse> producto = this.recetasRepository.findOneByTitulo("Sushi Roll California");

        assertTrue(producto.isEmpty());
        verify(this.recetasRepository).findOneByTitulo("Sushi Roll California");
    }

    @Test
    void testFindAllOrderByNombreAsc() {
        List<RecetaResponse> recetaoDB = List.of(
                new RecetaResponse(3, "Sushi Roll California"),
                new RecetaResponse(5, "Tacos al Pastor")
        );
        when(this.recetasRepository.findAllOrderByTituloAsc()).thenReturn(recetaoDB);

        List<RecetaResponse> recetas = this.recetasRepository.findAllOrderByTituloAsc();

        assertFalse(recetas.isEmpty());
        assertEquals(3, recetas.size());
        verify(this.recetasRepository).findAllOrderByTituloAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        List<RecetaResponse> recetaDB = List.of(
                new RecetaResponse(3, "Sushi Roll California")
        );
        when((this.recetasRepository.findByNombreContainingIgnoreCaseOrderByTituloAsc("Roll"))).thenReturn(recetaDB);

        List<RecetaResponse> recetas = this.recetasRepository.findByNombreContainingIgnoreCaseOrderByTituloAsc("Roll");

        assertFalse(recetas.isEmpty());
        assertEquals(1, recetas.size());
        verify(this.recetasRepository).findByNombreContainingIgnoreCaseOrderByTituloAsc("Roll");
    }


}
