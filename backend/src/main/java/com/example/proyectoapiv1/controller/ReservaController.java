package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Reserva;
import com.example.proyectoapiv1.model.dto.ReservaDTO;
import com.example.proyectoapiv1.service.Impl.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
@Tag(name = "Reservas", description = "Es necesario un JWT válido para utilizar los siguientes endpoints.")
public class ReservaController {
    @Autowired
    ReservaService reservaService;


    @Operation(summary = "Crear una reserva", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó la reserva con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un producto con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta crear una reserva incompleta o la fecha de inicio es posterior a la fecha de fin enviada.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaDTO reservaDTO) throws ResourceConflictException, ResourceNotFoundException, MessagingException {
        Reserva reserva = reservaService.agregarReserva(reservaDTO);
        reservaService.enviarMailDeReserva(reserva.getId());
        return ResponseEntity.ok(reserva);
    }


    @Operation(summary = "Listar reservas", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las reservas correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<ReservaDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }


    @Operation(summary = "Listar reservas por producto", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las reservas del producto correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un producto con el id enviado.",
                    content = @Content)})
    @GetMapping("/producto/{id}")
    public ResponseEntity<Set<ReservaDTO>> listarReservasPorProducto(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reservaService.listarReservasPorProducto(id));
    }


    @Operation(summary = "Listar reservas por usuario", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las reservas del usuario correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el id enviado.",
                    content = @Content)})
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Set<ReservaDTO>> listarReservasPorUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(id));
    }
}
