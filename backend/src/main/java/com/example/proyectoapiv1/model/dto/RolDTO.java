package com.example.proyectoapiv1.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RolDTO {
    private Long id;
    @NotNull
    private String nombre;
}
