package com.jao.recetas.controller;

import com.jao.recetas.dto.RecetaRequest;
import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.services.RecetasService;
import com.jao.recetas.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetasController {

    private final RecetasService recetasService;

    @GetMapping()
    public ResponseEntity <?> getAll(){
        List<RecetaResponse> recetas= this.recetasService.findAllOrderByTituloAsc();
        if (recetas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recetas);
    }


    @GetMapping(value ="/{id}")
    public ResponseEntity <?> getRecetasById(@PathVariable Integer id){
        Optional<RecetaResponse> recetas = recetasService.findRecetaById(id);
        if (recetas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la receta con id "+id);
        }
        return ResponseEntity.ok(recetas.get()) ;
    }

    @GetMapping("/buscar/{infix}")
    public ResponseEntity<?> findByTituloContaining(@PathVariable String infix) {
        List<RecetaResponse> recetas = this.recetasService.findByNombreContainingIgnoreCaseOrderByTituloAsc(infix);
        if (recetas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(recetas);
    }

    @PostMapping()
    public ResponseEntity <?> create(@RequestBody RecetaRequest recetaRequest) {
        try {
            RecetaResponse receta = this.recetasService.createReceta(recetaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(receta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }

    }

    @PutMapping(value ="/{id}")
    public ResponseEntity <?> update(@PathVariable Integer id, @RequestBody RecetaRequest recetaRequest){
        try {
            RecetaResponse receta = this.recetasService.createReceta(recetaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(receta);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity <String> detete(@PathVariable Integer id){
        if(this.recetasService.deleteReceta(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la receta con id "+id);

    }

}
