package com.example.proyectoapiv1.model.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationRequest {

    @NotNull
    private String email;
    @NotNull
    private String contrasenia;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String contrasenia) {
        this.email = email;
        this.contrasenia = contrasenia;
    }
}
