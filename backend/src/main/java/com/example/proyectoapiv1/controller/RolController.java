package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Rol;
import com.example.proyectoapiv1.model.dto.RolDTO;
import com.example.proyectoapiv1.service.Impl.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Roles", description = "Es necesario un JWT válido para utilizar los siguientes endpoints y contar con un usuario con rol ADMIN.")
public class RolController {
    @Autowired
    RolService rolService;


    @Operation(summary = "Crear un rol", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó el rol con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RolDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido o no cuenta con los permisos necesarios como rol ADMIN.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta crear un rol incompleto.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody RolDTO rolDTO) throws ResourceConflictException {
        return ResponseEntity.ok(rolService.agregarRol(rolDTO));
    }


    @Operation(summary = "Listar roles", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los roles correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido o no cuenta con los permisos necesarios como rol ADMIN.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<RolDTO>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }
}
