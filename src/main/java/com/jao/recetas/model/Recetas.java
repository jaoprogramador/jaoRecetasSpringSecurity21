package com.jao.recetas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jao.recetas.dto.CategoriaRequest;
import com.jao.recetas.dto.RecetaRequest;
import com.jao.recetas.dto.RecetaResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recetas")
@Getter @Setter

public class Recetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;
    private Integer tiempoPreparacionMinutos;
    private Integer porciones;
    private List<String> ingredientes;
    private String instrucciones;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaModificacion;
    private String activo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    @JsonBackReference
    private Categoria categoria;

    public Recetas(RecetaRequest request) {
        this.titulo = request.getTitulo();
    }

    public Recetas(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Recetas(RecetaResponse recetaResponse, RecetaRequest request) {
    }

    @Override
    public String toString() {
        return "Recetas{" +
                "id=" + id +
                ", categoriaId=" + categoriaId +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tiempoPreparaciónMinutos=" + tiempoPreparacionMinutos +
                ", porciones=" + porciones +
                ", ingredientes=" + ingredientes +
                ", instrucciones='" + instrucciones + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", fechaModificación=" + fechaModificacion +
                ", categoria=" + categoria +
                '}';
    }
}
