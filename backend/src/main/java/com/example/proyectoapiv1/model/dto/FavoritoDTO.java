package com.example.proyectoapiv1.model.dto;

import com.example.proyectoapiv1.model.Producto;
import com.example.proyectoapiv1.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FavoritoDTO {
    private Long id;
    @NotNull
    private Producto producto;
    @NotNull
    private Usuario usuario;
}
