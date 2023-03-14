package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private  String titulo;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name= "categoria_id")
    @NotNull
    private Categoria categoria;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name= "ciudad_id")
    @NotNull
    private Ciudad ubicacion;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    private Set<Imagen> imagenes = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "descripcion_id", referencedColumnName = "id")
    @NotNull
    private Descripcion descripcion;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    private Set<Politica> politicas = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Puntuacion> puntuaciones = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Favorito> favoritos = new HashSet<>();

    @NotNull
    private Double latitud;
    @NotNull
    private Double longitud;
    @NotNull
    private String direccion;
    @NotNull
    private Double distanciaCentro;

    public Producto(String titulo, Categoria categoria, Ciudad ubicacion, Set<Imagen> imagenes, Descripcion descripcion, Set<Caracteristica> caracteristicas, Set<Reserva> reservas, Set<Politica> politicas, Set<Puntuacion> puntuaciones, Double latitud, Double longitud, String direccion, Double distanciaCentro) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.imagenes = imagenes;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.reservas = reservas;
        this.politicas = politicas;
        this.puntuaciones = puntuaciones;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.distanciaCentro = distanciaCentro;
    }

    public Producto(){

    }


}

