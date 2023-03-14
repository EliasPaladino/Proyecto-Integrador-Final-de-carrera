package com.example.proyectoapiv1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate fechaInicio;
    @NotNull
    private LocalDate fechaFin;
    @NotNull
    private Time horaIngreso;
    @NotNull
    @Lob
    private String comentario;
    @NotNull
    private Boolean estadoVacunacion;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @NotNull
    private Producto producto;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @NotNull
    private Usuario usuario;

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, Time horaIngreso, String comentario, Boolean estadoVacunacion,  Producto producto, Usuario usuario) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaIngreso = horaIngreso;
        this.comentario = comentario;
        this.estadoVacunacion = estadoVacunacion;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Reserva() {
    }
}
