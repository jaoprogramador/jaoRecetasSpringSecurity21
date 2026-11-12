package com.jao.recetas.junit.service;
import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.repository.CategoriasRepository;
import com.jao.recetas.repository.RecetasRepository;
import com.jao.recetas.services.CategoriasServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class CategoriaServiceTest {

     @Mock
     private CategoriasRepository categoriaRepository;

     @Mock
     private RecetasRepository recetasRepository;

     @InjectMocks
     private CategoriasServiceImpl categoriaService;

     @Test
     void testFindCategoriaById_CategoriaExists() {
         CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
         when(this.categoriaService.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));

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

     @Test
     void testFindOneByNombre_CategoriaExists() {
         CategoriaResponse categoriaDB = new CategoriaResponse(1, "Italiana");
         when(this.categoriaRepository.findOneByNombre("Italiana")).thenReturn(Optional.of(categoriaDB));

         Optional<CategoriaResponse> categoria = this.categoriaService.findOneByNombre("Italiana");

         assertTrue(categoria.isPresent());
         assertEquals(1, categoria.get().getId());
         verify(this.categoriaRepository).findOneByNombre("Italiana");
     }
}
