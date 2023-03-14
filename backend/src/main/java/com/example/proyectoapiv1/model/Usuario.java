package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 7)
    private String contrasenia;
    @NotNull
    private String ciudad;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Puntuacion> puntuaciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Favorito> favoritos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();


    public Usuario(String nombre, String apellido, String email, String contrasenia, String ciudad, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
        this.ciudad = ciudad;
        this.rol = rol;
    }

    public Usuario() {
    }
}
