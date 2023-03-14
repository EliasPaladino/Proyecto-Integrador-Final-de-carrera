package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.dto.FavoritoDTO;
import com.example.proyectoapiv1.service.Impl.FavoritoService;
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
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
@Tag(name = "Favoritos", description = "Es necesario un JWT válido para utilizar los siguientes endpoints.")
public class FavoritoController {

    @Autowired
    FavoritoService favoritoService;


    @Operation(summary = "Crear un favorito", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó el favorito con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta crear un favorito incompleto.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<String> crearFavorito(@Valid @RequestBody FavoritoDTO favoritoDTO) throws ResourceConflictException, ResourceNotFoundException {
        favoritoService.agregarFavorito(favoritoDTO);
        return ResponseEntity.ok("El favorito se agregó correctamente");
    }


    @Operation(summary = "Listar favoritos", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los favoritos correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<FavoritoDTO>> listarFavoritos() {
        return ResponseEntity.ok(favoritoService.listarFavoritos());
    }


    @Operation(summary = "Listar favoritos por usuario", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los favoritos del usuario correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el id enviado.",
                    content = @Content)})
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Set<FavoritoDTO>> listarFavoritosPorUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(favoritoService.listarFavoritosPorUsuario(id));
    }

    /*@DeleteMapping
    public ResponseEntity<String> eliminarPorUsuarioYProducto(@RequestParam ("idUsuario") Long idUsuario, @RequestParam("idProducto") Long idProducto) throws BadRequestException, ResourceNotFoundException {
        Boolean eliminado = favoritoService.eliminarFavoritoPorUsuarioYProducto(idUsuario, idProducto);
        if(eliminado){
            return ResponseEntity.ok("El usuario con id " + idUsuario + " eliminó el favorito del producto con id: " + idProducto);
        }else{
            return ResponseEntity.ok("El producto no se encontró en los favoritos del usuario con id " + idUsuario);
        }
    }

     */

    @Operation(summary = "Eliminar favorito", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se eliminó el favorito con éxito.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "El id del usuario o el id del producto no pueden ser negativos.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un producto con el id enviado o el usuario no tiene como favorito al producto con el id enviado o no existe un usuario con el id enviado.",
                    content = @Content)})
    @DeleteMapping
    public ResponseEntity<String> eliminarPorUsuarioYProducto(@RequestParam ("idUsuario") Long idUsuario, @RequestParam("idProducto") Long idProducto) throws BadRequestException, ResourceNotFoundException {
        favoritoService.eliminarFavoritoPorUsuarioYProducto(idUsuario, idProducto);
        return ResponseEntity.ok("El usuario con id " + idUsuario + " eliminó el favorito del producto con id " + idProducto);
    }
}
