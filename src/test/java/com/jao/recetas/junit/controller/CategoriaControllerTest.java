package com.jao.recetas.junit.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jao.recetas.controller.CategoriasController;
import com.jao.recetas.dto.CategoriaRequest;
import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.util.exception.InvalidOperationException;
import com.jao.recetas.util.exception.ResourceNotFoundException;
import com.jao.recetas.services.CategoriasService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = CategoriasController.class)
public class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriasService categoriaService;

    @Test
    void testFindCategoriaById() throws Exception {
        int id = 1;
        CategoriaResponse categoria = new CategoriaResponse(1, "Italiana");
        when(this.categoriaService.findCategoriaById(id)).thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Italiana"));
    }

    @Test
    void testFindCategoriaByNombre() throws Exception {
        String nombre = "Mexicana";
        CategoriaResponse categoria = new CategoriaResponse(2, "Mexicana");
        when(this.categoriaService.findOneByNombre(nombre)).thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    @Test
    void testFindCategoriaNotFound() throws Exception {
        int id = 1;
        when(this.categoriaService.findCategoriaById(id)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la categoría buscada"));
    }

    @Test
    void testFindCategoriaWithoutParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindCategoriaWithTwoParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", "1")
                        .param("nombre", "Italiana"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindAll() throws Exception {
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1, "Italiana"),
                new CategoriaResponse(2, "Mexicana")
        );
        when(this.categoriaService.findAllOrderByNombreAsc()).thenReturn(categorias);

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindAllNoContent() throws Exception {
        when(this.categoriaService.findAllOrderByNombreAsc()).thenReturn(List.of());

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByNombreContaining() throws Exception {
        String infix = "ciana";
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1, "Italiana"),
                new CategoriaResponse(2, "Mexicana")
        );
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(categorias);

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindByNombreContainingNoContent() throws Exception {
        String infix = "Tego";
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(List.of());

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreate() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Checa");
        CategoriaResponse categoria = new CategoriaResponse(1, "Checa");
        when(this.categoriaService.createCategoria(any(CategoriaRequest.class))).thenReturn(categoria);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Checa"));
    }

    @Test
    void testUpdate() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        CategoriaResponse categoria = new CategoriaResponse(id, "Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenReturn(categoria);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoría modificada"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenThrow(new ResourceNotFoundException("Categoría no existe"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Categoría no existe"));
    }

    @Test
    void testUpdateMismoNombre() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenThrow(new InvalidOperationException("Existe una categoría con el mismo nombre"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Existe una categoría con el mismo nombre"));
    }

    @Test
    void testDelete() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha eliminado correctamente la categoría con id: 1"));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un categoría con id: 1"));
    }

    @Test
    void testDeleteWithRecetas() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenThrow(new InvalidOperationException("La categoría tiene recetas"));

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La categoría tiene recetas"));
    }
}