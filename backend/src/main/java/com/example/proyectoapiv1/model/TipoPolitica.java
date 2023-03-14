package com.example.proyectoapiv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "tipos_politicas")
@Getter
@Setter
public class TipoPolitica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tipoPolitica;

    @OneToMany(mappedBy = "tipoPolitica", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Politica> politicas;

    public TipoPolitica(String tipoPolitica, Set<Politica> politicas) {
        this.tipoPolitica = tipoPolitica;
        this.politicas = politicas;
    }

    public TipoPolitica() {
    }
}
