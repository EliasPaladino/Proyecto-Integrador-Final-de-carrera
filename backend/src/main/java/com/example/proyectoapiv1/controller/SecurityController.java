package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.dto.UsuarioDTO;
import com.example.proyectoapiv1.security.JwtUtil;
import com.example.proyectoapiv1.model.security.AuthenticationRequest;
import com.example.proyectoapiv1.model.security.AuthenticationResponse;
import com.example.proyectoapiv1.service.Impl.security.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Seguridad", description = "Endpoints para gestión de usuarios.")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;


    @Operation(summary = "Iniciar sesión con un usuario válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario ingresó con éxito.",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationRequest.class))}),
            @ApiResponse(responseCode = "401", description = "No se encontró un usuario con las credenciales ingresadas.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con las credenciales ingresadas.",
                content = @Content)})
    //nos permite iniciar sesion y retorna el token
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getContrasenia()));

            final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            UsuarioDTO usuarioDTO = userDetailsServiceImpl.buscarUsuarioPorEmail(authenticationRequest.getEmail());

            return ResponseEntity.ok(new AuthenticationResponse(jwt, usuarioDTO.getNombre(), usuarioDTO.getApellido(), usuarioDTO.getId(), usuarioDTO.getCiudad(), usuarioDTO.getRol().getNombre()));

        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            /*throw new Exception("Credenciales Incorrectas", e);*/
        }
    }


    @Operation(summary = "Crear un nuevo usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El usuario se creó con éxito.",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Intenta crear un usuario incompleto o el email ingresado ya fue registrado.",
                content = @Content)})
    //metodo para crear un usuario, aqui es donde tendre que enviar el mail de confirmacion de registro
    @PostMapping("/signup")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws ResourceConflictException, MessagingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsServiceImpl.agregarUsuario(usuarioDTO));
    }

}
