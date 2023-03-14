package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @NotNull
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    public Favorito(Usuario usuario, Producto producto) {
        this.usuario = usuario;
        this.producto = producto;
    }

    public Favorito() {
    }
}
