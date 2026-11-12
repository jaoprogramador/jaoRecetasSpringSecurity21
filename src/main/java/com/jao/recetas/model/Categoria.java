package com.jao.recetas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jao.recetas.dto.CategoriaRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String procedencia;

    @Column(nullable = false, length = 50)
    private String tipoAlimento;

    @OneToMany(mappedBy = "categoria")
    @JsonManagedReference
    private List<Recetas> recetas;

    public Categoria(CategoriaRequest request) {
        this.nombre = request.getNombre();
    }

    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
