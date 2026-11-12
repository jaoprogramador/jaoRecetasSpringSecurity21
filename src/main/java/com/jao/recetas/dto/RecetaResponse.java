package com.jao.recetas.dto;

import com.jao.recetas.model.Recetas;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class RecetaResponse {
    private Integer id;
    private Integer categoriaId;
    private String titulo;
    private String descripcion;
    private Integer tiempoPreparacionMinutos;
    private Integer porciones;

    public RecetaResponse(Recetas recetaModificado) {
    }

    @Override
    public String toString() {
        return "RecetaResponse{" +
                "id=" + id +
                ", categoriaId=" + categoriaId +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tiempoPreparacionMinutos=" + tiempoPreparacionMinutos +
                ", porciones=" + porciones +
                '}';
    }

    public RecetaResponse(Integer id, Integer categoriaId, String titulo, String descripcion, Integer tiempoPreparacionMinutos,Integer porciones) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoPreparacionMinutos= tiempoPreparacionMinutos;
        this.porciones = porciones;
    }
    public RecetaResponse(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;


    }
}
