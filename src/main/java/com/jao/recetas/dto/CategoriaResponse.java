package com.jao.recetas.dto;
import com.jao.recetas.model.Categoria;
import com.jao.recetas.model.Recetas;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@NoArgsConstructor
public class CategoriaResponse {
    private Integer id;
    private String nombre;
    private String procedencia;
    private String tipoAlimento;
    private List<Recetas> recetas;

    public CategoriaResponse(Integer id, String nombre, String procedencia, String tipoAlimento) {
        this.id = id;
        this.nombre = nombre;
        this.procedencia = procedencia;
        this.tipoAlimento = tipoAlimento;
        this.recetas = null;
    }
    public CategoriaResponse(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;


    }
    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }


    @Override
    public String toString() {
        return "CategoriaResponse{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", procedencia='" + procedencia + '\'' +
                ", tipoAlimento='" + tipoAlimento + '\'' +
                ", recetas=" + recetas +
                '}';
    }
}
