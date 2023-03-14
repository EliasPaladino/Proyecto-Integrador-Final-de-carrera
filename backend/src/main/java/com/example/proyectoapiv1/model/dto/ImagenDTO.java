package com.example.proyectoapiv1.model.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ImagenDTO {
    private Long id;
    private String titulo;
    @NotNull
    private String url;
    @NotNull
    private Boolean fotoPrincipal;
}
