package com.jao.recetas.dto;

import com.jao.recetas.model.Recetas;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter @Setter @NoArgsConstructor
public class CategoriaRequest {
    private Integer id;
    private String nombre;
    private String procedencia;
    private String tipoAlimento;
    private List<Recetas> recetas;

    public CategoriaRequest(Integer id, String nombre, String procedencia, String tipoAlimento, List<Recetas> recetas) {
        this.id = id;
        this.nombre = nombre;
        this.procedencia = procedencia;
        this.tipoAlimento = tipoAlimento;
        this.recetas = recetas;
    }
    public CategoriaRequest(String nombre) {
        this.nombre = nombre;

    }
}
