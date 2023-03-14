package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "politicas")
@Getter
@Setter
public class Politica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String politica;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_politica_id")
    @NotNull
    private TipoPolitica tipoPolitica;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    public Politica(String titulo, String politicas, Producto producto) {
        this.politica = politicas;
        this.producto = producto;
    }

    public Politica() {
    }
}
