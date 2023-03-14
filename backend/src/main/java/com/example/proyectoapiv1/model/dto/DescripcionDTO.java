package com.example.proyectoapiv1.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class DescripcionDTO {
        private Long id;
        @NotNull
        private String titulo;
        @NotNull
        @Lob
        private String cuerpo;
}
