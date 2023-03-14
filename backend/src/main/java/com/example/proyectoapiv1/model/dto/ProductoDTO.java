package com.example.proyectoapiv1.model.dto;

import com.example.proyectoapiv1.model.*;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@Setter
public class ProductoDTO {
    private Long id;
    @NotNull
    private String titulo;
    @NotNull
    private Categoria categoria;
    @NotNull
    private Ciudad ubicacion;
    @NotNull
    private Set<ImagenDTO> imagenes;
    @NotNull
    private Descripcion descripcion;
    @NotNull
    private Set<CaracteristicaDTO> caracteristicas;
    @NotNull
    private Set<PoliticaDTO> politicas;
    @NotNull
    private Double latitud;
    @NotNull
    private Double longitud;
    @NotNull
    private String direccion;
    @NotNull
    private Double distanciaCentro;
}

