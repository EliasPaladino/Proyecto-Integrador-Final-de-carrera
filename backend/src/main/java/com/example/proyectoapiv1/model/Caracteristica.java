package com.example.proyectoapiv1.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "caracteristicas")
@Getter
@Setter
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String icono;
    @NotNull
    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    public Caracteristica(String icono, String nombre, Producto producto) {
        this.icono = icono;
        this.nombre = nombre;
        this.producto = producto;
    }

    public Caracteristica() {
    }
}
