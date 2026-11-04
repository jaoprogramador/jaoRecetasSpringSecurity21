package com.jao.recetas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RecetaDTO {

    private String titulo;
    private String descripcion;
    private Integer tiempoPreparacionMinutos;
    private Integer porciones;

    @Override
    public String toString() {
        return "RecetaDTO{" +

                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tiempoPreparacionMinutos=" + tiempoPreparacionMinutos +
                ", porciones=" + porciones +
                '}';
    }
}
