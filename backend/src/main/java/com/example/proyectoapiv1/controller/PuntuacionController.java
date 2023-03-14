package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.dto.PuntuacionDTO;
import com.example.proyectoapiv1.service.Impl.PuntuacionService;
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
@RequestMapping("/puntuaciones")
@CrossOrigin(origins = "*")
@Tag(name = "Puntuaciones")
public class PuntuacionController {
    @Autowired
    PuntuacionService puntuacionService;


    @Operation(summary = "Crear puntuación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó la puntuación con éxito.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PuntuacionDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Se intenta crear una puntuación incompleta.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> crearPuntuacion(@Valid @RequestBody PuntuacionDTO puntuacionDTO) throws ResourceConflictException {
        puntuacionService.agregarPuntuacion(puntuacionDTO);
        return ResponseEntity.ok("La puntuación se agregó al producto");
    }


    @Operation(summary = "Listar puntuaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las puntuaciones correctamente.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<PuntuacionDTO>> listarPuntuaciones() {
        return ResponseEntity.ok(puntuacionService.listarPuntuaciones());
    }


}
