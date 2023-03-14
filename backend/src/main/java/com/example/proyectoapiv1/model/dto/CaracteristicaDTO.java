package com.example.proyectoapiv1.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CaracteristicaDTO {
    private Long id;
    @NotNull
    private String icono;
    @NotNull
    private String nombre;
    //private Producto producto;
}
