package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ciudades")
@Getter
@Setter
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String pais;

    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    public Ciudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Ciudad() {
    }
}
