package com.example.proyectoapiv1.model.security;

import lombok.Getter;

@Getter
public class AuthenticationResponse {

    private String nombre;
    private String apellido;
    private Long usuarioId;
    private String ciudad;
    private String rol;
    private final String jwt;

    public AuthenticationResponse(String jwt, String nombre, String apellido, Long usuarioId, String ciudad, String rol) {
        this.jwt = jwt;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuarioId = usuarioId;
        this.ciudad = ciudad;
        this.rol = rol;
    }
}
