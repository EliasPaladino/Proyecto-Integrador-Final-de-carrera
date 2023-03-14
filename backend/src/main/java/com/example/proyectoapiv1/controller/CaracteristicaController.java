package com.example.proyectoapiv1.controller;


import com.example.proyectoapiv1.model.dto.CaracteristicaDTO;
import com.example.proyectoapiv1.service.Impl.CaracteristicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/caracteristicas")
@CrossOrigin(origins = "*")
@Tag(name = "Características")
public class CaracteristicaController {

    @Autowired
    CaracteristicaService caracteristicaService;


    @Operation(summary = "Listar caracteristicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las caracteristicas correctamente.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<CaracteristicaDTO>> listarCaracteristicasNoRepetidas(){
        return ResponseEntity.ok(caracteristicaService.listarCaracteristicasNoRepetidas());
    }
}
