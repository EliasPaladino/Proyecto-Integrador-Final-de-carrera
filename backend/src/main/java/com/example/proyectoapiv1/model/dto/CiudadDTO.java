package com.example.proyectoapiv1.model.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CiudadDTO {
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String pais;
}
