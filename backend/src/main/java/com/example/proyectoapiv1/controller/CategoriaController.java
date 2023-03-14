package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Categoria;
import com.example.proyectoapiv1.model.dto.CategoriaDTO;
import com.example.proyectoapiv1.service.Impl.CategoriaService;
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
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
@Tag(name = "Categorías")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;


    @Operation(summary = "Crear categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó la categoría con éxito.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Se intenta crear una categoría incompleta.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws ResourceConflictException{
        return ResponseEntity.ok(categoriaService.agregarCategoria(categoriaDTO));
    }


    @Operation(summary = "Listar categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron las categorías correctamente.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<Set<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }


    @Operation(summary = "Modificar categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modificó la categoría con éxito.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontró una categoría con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta modificar una categoría incompleta. Se deben enviar todos los datos.",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Categoria> modificarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws ResourceNotFoundException, ResourceConflictException {
        return ResponseEntity.ok(categoriaService.editarCategoria(categoriaDTO));
    }


    @Operation(summary = "Eliminar categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se eliminó la categoría con éxito.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "El id de la categoría no puede ser negativo.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró una categoría con el id enviado.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Se eliminó la categoría con id: " + id);
    }
}
