package com.jao.recetas.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jao.recetas.dto.RecetaRequest;
import com.jao.recetas.dto.RecetaResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecetasControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static Integer createdId;

    @Test
    @Order(1)
    void testFindRecetaById() throws Exception {
        int id = 3;

        this.mockMvc.perform(get("/api/recetas/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titulo").value("Sushi Roll California"));
    }

    @Test
    @Order(2)
    void testFindRecetaByTitulo() throws Exception {
        String nombre = "Producto 2";

        this.mockMvc.perform(get("/api/recetas/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(2))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    @Test
    @Order(3)
    void testFindRecetasNotFound() throws Exception {
        String nombre = "Mouse";

        this.mockMvc.perform(get("/api/recetas/find")
                        .param("nombre", nombre))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la receta buscado"));
    }

    @Test
    @Order(4)
    void testFindRecetaWithoutParams() throws Exception {
        this.mockMvc.perform(get("/api/recetas/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    @Order(5)
    void testFindRecetaWithTwoParams() throws Exception {
        this.mockMvc.perform(get("/api/recetas/find")
                        .param("id", "1")
                        .param("titulo", "Mouse"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    @Order(6)
    void testFindAll() throws Exception {
        this.mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(9));
    }

    @Test
    @Order(7)
    void testFindByRecetasContaining() throws Exception {
        String infix = "Duct";

        this.mockMvc.perform(get("/api/recetas/" + infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(9));
    }

    @Test
    @Order(8)
    void testFindByRecetasContainingNocontent() throws Exception {
        String infix = "Exter";

        this.mockMvc.perform(get("/api/recetas/" + infix))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void testFindByCategoria() throws Exception {
        int id  = 1;

        this.mockMvc.perform(get("/api/recetas/categoria/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @Order(10)
    void testFindByCategoriaNocontent() throws Exception {
        int id = 10;

        this.mockMvc.perform(get("/api/recetas/categoria/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(11)
    void testCreate() throws Exception {
        RecetaRequest request = new RecetaRequest(1, "Burritos", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MvcResult result = this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(greaterThan(9)))
                .andExpect(jsonPath("$.titulo").value("Burritos"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        RecetaResponse productoResponse = objectMapper.readValue(responseBody, RecetaResponse.class);
        createdId = productoResponse.getId();
    }

    @Test
    @Order(12)
    void testCreate_ReturnExisting() throws Exception {
        RecetaRequest request = new RecetaRequest(1, "Producto 2", 10);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").value(2))
                .andExpect(jsonPath("$.nombre").value("Producto 2"));
    }

    @Test
    @Order(13)
    void testCreateInexistingCategoria() throws Exception {
        RecetaRequest recetaRequest = new RecetaRequest(5, "Teclado", 10);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No existe la categoría indicada, no se puede crear el producto"));
    }

    @Test
    @Order(14)
    void testUpdate() throws Exception {
        int id = createdId;
        RecetaRequest request = new RecetaRequest(1, "Mouse vertical", 15);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/recetas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(id))
                .andExpect(jsonPath("$.nombre").value("Mouse vertical"));
    }

    @Test
    @Order(15)
    void testUpdateNotFound() throws Exception {
        int id = 15;
        RecetaRequest request = new RecetaRequest(1, "Mouse vertical", 15);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/productos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se puede efectuar la modificación, el producto no existe"));
    }

    @Test
    @Order(16)
    void testUpdateMismoNombre() throws Exception {
        int id = createdId;
        RecetaRequest request = new RecetaRequest(1, "Producto 2", 10);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/recetas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede modificar el producto porque existe otro con el mismo nombre"));
    }

    @Test
    @Order(17)
    void testDelete() throws Exception {
        int id = createdId;

        this.mockMvc.perform(delete("/api/recetas/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Se ha eliminado correctamente el producto con id: %d", createdId)));
    }

    @Test
    @Order(18)
    void testDeleteNotFound() throws Exception {
        int id = 15;

        this.mockMvc.perform(delete("/api/recetas/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un producto con id: 15"));
    }
}
