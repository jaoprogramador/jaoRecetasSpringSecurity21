package com.jao.recetas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class RecetaRequest {
    private Integer id;
    private Integer categoriaId;
    private String titulo;
    private String descripcion;
    private Integer tiempoPreparacionMinutos;
    private Integer porciones;

    public RecetaRequest(Integer id, Integer categoriaId, String titulo, String descripcion, Integer tiempoPreparacionMinutos, Integer porciones) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoPreparacionMinutos = tiempoPreparacionMinutos;
        this.porciones = porciones;
    }
    public RecetaRequest(Integer id, String titulo,Integer categoriaId ) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.titulo = titulo;

    }
}
