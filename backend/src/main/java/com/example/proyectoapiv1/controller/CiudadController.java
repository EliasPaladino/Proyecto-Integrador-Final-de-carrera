package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Ciudad;
import com.example.proyectoapiv1.model.dto.CiudadDTO;
import com.example.proyectoapiv1.service.Impl.CiudadService;
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
@RequestMapping("/ciudades")
@CrossOrigin(origins = "*")
@Tag(name = "Ciudades")
public class CiudadController {
    @Autowired
    CiudadService ciudadService;


    @Operation(summary = "Crear ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó la ciudad con éxito.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CiudadDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Se intenta crear una ciudad incompleta.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Ciudad> crearCiudad(@Valid @RequestBody CiudadDTO ciudadDTO) throws ResourceConflictException {
        return ResponseEntity.ok(ciudadService.agregarCiudad(ciudadDTO));
    }


    @Operation(summary = "Listar ciudades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las ciudades correctamente.",
                    content = @Content)})
    //endpoint para el select de ciudades
    @GetMapping
    public ResponseEntity<Set<CiudadDTO>> listarCiudades() {
        return ResponseEntity.ok(ciudadService.listarCiudades());
    }


    @Operation(summary = "Buscar ciudad por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontró la ciudad con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró una ciudad con el id enviado.",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<CiudadDTO> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(ciudadService.buscarCiudadPorId(id));
    }
}
