package com.example.proyectoapiv1.model.dto;

import com.example.proyectoapiv1.model.Producto;
import com.example.proyectoapiv1.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
public class ReservaDTO {
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
    @NotNull
    private Producto producto;
    @NotNull
    private Usuario usuario;
}
