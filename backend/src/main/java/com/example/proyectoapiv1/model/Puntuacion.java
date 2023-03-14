package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "puntuaciones")
@Getter
@Setter
public class Puntuacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer puntuacion;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @NotNull
    private Usuario usuario;

    public Puntuacion(int puntuacion, Producto producto, Usuario usuario) {
        this.puntuacion = puntuacion;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Puntuacion() {
    }
}
