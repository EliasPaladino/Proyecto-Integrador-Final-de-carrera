package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();


    public Rol(String nombre, Set<Usuario> usuarios) {
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

    public Rol() {
    }
}
