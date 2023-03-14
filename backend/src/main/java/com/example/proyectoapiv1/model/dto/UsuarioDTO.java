package com.example.proyectoapiv1.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private String ciudad;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 7, message = "La contraseña debe tener más de 6 caracteres")
    private String contrasenia;
    private RolDTO rol;
}
