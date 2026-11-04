package com.jao.recetas.integration.service;
import com.jao.recetas.dto.RecetaRequest;
import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.services.RecetasService;
import com.jao.recetas.util.exception.InvalidOperationException;
import com.jao.recetas.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecetaserviceTest {
    @Autowired
    private RecetasService recetasService;

    private static Integer createdId;

    @Test
    @Order(1)
    void testFindOneById_RecetaExists() {
        Integer recetaId = 4;

        Optional<RecetaResponse> receta = recetasService.findRecetaById(recetaId);

        assertTrue(receta.isPresent());
        assertEquals("Producto 4", receta.get().getTitulo());
    }

    @Test
    @Order(2)
    void testFindOneById_RecetaNotFound() {
        Integer recetaId = 14;

        Optional<RecetaResponse> receta = recetasService.findRecetaById(recetaId);

        assertTrue(receta.isEmpty());
    }

    @Test
    @Order(3)
    void testFindOneByNombre_RecetaExists() {
        String nombre = "Tacos al Pastor";

        Optional<RecetaResponse> producto = this.recetasService.findOneByTitulo(nombre);

        assertTrue(producto.isPresent());
        assertEquals(5, producto.get().getId());
    }

    @Test
    @Order(4)
    void testFindOneByNombre_ProductoNotFount() {
        String nombre = "Receta 14";

        Optional<RecetaResponse> producto = this.recetasService.findOneByTitulo(nombre);

        assertTrue(producto.isEmpty());
    }

    @Test
    @Order(5)
    void testFindAllOrderByNombre() {
        List<RecetaResponse> productos = this.recetasService.findAllOrderByTituloAsc();

        assertEquals(2, productos.size());
    }

    @Test
    @Order(6)
    void testFindByNombreContainingIgnoreCaseOrderByNombre() {
        String infix = "DucTo";

        List<RecetaResponse> recetas = this.recetasService.findByNombreContainingIgnoreCaseOrderByTituloAsc(infix);

        assertEquals(9, recetas.size());
    }

    @Test
    @Order(7)
    void testFindByCategoriaIdOrderByNombre() {
        Integer categoriaId = 3;

        List<RecetaResponse> recetas = this.recetasService.findByCategoriaIdOrderByTituloAsc(categoriaId);

        assertEquals(3, recetas.size());
    }

    @Test
    @Order(8)
    void testCreateReceta_CreateNew() {
        RecetaRequest request = new RecetaRequest(5, "Tacos al Pastor", 2);

        RecetaResponse receta = this.recetasService.createReceta(request);
        createdId = receta.getId();

        assertNotNull(receta);
        assertTrue(receta.getId() > 9);
    }

    @Test
    @Order(9)
    void testCreateReceta_ReturnExisting() {
        RecetaRequest request = new RecetaRequest(6, "Frijoles", 2);

        RecetaResponse producto = this.recetasService.createReceta(request);

        assertNotNull(producto);
        assertEquals(1, producto.getId());
    }
    @Test
    @Order(10)
    void testCreateReceta_CategoriaNotFound() {
        RecetaRequest request = new RecetaRequest(7, "Tacos", 11);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                recetasService.createReceta(request));

        assertEquals("No existe la categoría indicada, no se puede crear la receta", exception.getMessage());
    }

    @Test
    @Order(11)
    void testUpdateReceta_RecetaUpdated() {
        Integer recetaId = createdId;
        RecetaRequest request = new RecetaRequest(7, "Tacos", 10);

        RecetaResponse receta = this.recetasService.updateReceta(recetaId, request);

        assertNotNull(receta);
        assertEquals(recetaId, receta.getId());
    }

    @Test
    @Order(12)
    void testUpdateReceta_RecetaNotFound() {
        Integer recetaId = 14;
        RecetaRequest request = new RecetaRequest(3, "Birria", 12);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                this.recetasService.updateReceta(recetaId, request));

        assertEquals("No se puede efectuar la modificación, la receta no existe", exception.getMessage());
    }

    @Test
    @Order(13)
    void testUpdateReceta_DuplicatedName() {
        Integer recetaId = createdId;
        RecetaRequest request = new RecetaRequest(5, "Tacos al Pastor", 10);

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () ->
                this.recetasService.updateReceta(recetaId, request));

        assertEquals("No se puede modificar la receta porque existe otro con el mismo nombre", exception.getMessage());
    }

    @Test
    @Order(14)
    void testUpdateReceta_CategoriaNotExists() {
        Integer recetaId = createdId;
        RecetaRequest request = new RecetaRequest(5, "Mojito", 10);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                this.recetasService.updateReceta(recetaId, request));

        assertEquals("No existe la categoría indicada, no se puede modificar la receta", exception.getMessage());
    }

    @Test
    @Order(15)
    void testDeleteReceta_RecetaDeleted() {
        Integer recetaId = createdId;

        boolean result = this.recetasService.deleteReceta(recetaId);

        assertTrue(result);
    }

    @Test
    @Order(16)
    void testDeleteReceta_RecetaNotFound() {
        Integer recetaId = createdId;

        boolean result = this.recetasService.deleteReceta(recetaId);

        assertFalse(result);
    }
}