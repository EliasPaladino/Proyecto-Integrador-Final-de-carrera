package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "descripciones")
@Getter
@Setter
public class Descripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String titulo;
    @NotNull
    @Lob //para que la columna se cree con tipo de dato text
    private String cuerpo;

    public Descripcion(String titulo, String cuerpo) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }

    public Descripcion() {
    }
}
