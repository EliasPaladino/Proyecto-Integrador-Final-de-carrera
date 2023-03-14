package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "imagenes")
@Getter
@Setter
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @NotNull
    private String url;

    @NotNull
    private Boolean fotoPrincipal;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    public Imagen(String titulo, String url, Boolean fotoPrincipal, Producto producto) {
        this.titulo = titulo;
        this.url = url;
        this.fotoPrincipal = fotoPrincipal;
        this.producto = producto;
    }

    public Imagen() {
    }
}
