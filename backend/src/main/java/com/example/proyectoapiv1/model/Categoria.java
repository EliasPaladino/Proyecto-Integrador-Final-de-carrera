package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    @NotNull
    private String urlImagen;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    public Categoria(String titulo, String descripcion, String urlImagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }

    public Categoria() {
    }

}
