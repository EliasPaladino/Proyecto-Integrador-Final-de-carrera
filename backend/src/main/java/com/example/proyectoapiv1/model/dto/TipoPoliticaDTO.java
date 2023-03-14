package com.example.proyectoapiv1.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class TipoPoliticaDTO {
    private Long id;
    @NotNull
    private String tipoPolitica;
}
