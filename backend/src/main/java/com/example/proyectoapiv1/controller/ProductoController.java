package com.example.proyectoapiv1.controller;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.dto.ProductoDTO;
import com.example.proyectoapiv1.service.Impl.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "Para utilizar los endpoints con /admin es necesario un JWT válido y de un usuario con rol ADMIN.")
public class ProductoController {

    @Autowired
    ProductoService productoService;


    @Operation(summary = "Crear un producto", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se creó el producto con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido o no tiene los permisos necesarios como rol de ADMIN.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta crear un producto incompleto.",
                    content = @Content) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<String> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) throws ResourceConflictException {
        productoService.agregarProducto(productoDTO);
        return ResponseEntity.ok("Se creó el producto correctamente");
    }


    @Operation(summary = "Listar productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los productos correctamente.",
                    content = @Content)})
    //endpoint para productos aleatorios
    @GetMapping("/listar")
    public ResponseEntity<Set<ProductoDTO>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }


    @Operation(summary = "Modificar producto", description = "Se necesita enviar un JWT válido en el header de la petición para utilizar el método.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modificó el producto con éxito.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class))}),
            @ApiResponse(responseCode = "403", description = "No se recibió un JWT válido o no tiene los permisos necesarios como rol de ADMIN.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un producto con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Se intenta modificar un producto incompleto. Se deben enviar todos los datos incluido el id.",
                    content = @Content)})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin")
    public ResponseEntity<ProductoDTO> actualizarProducto(@Valid @RequestBody ProductoDTO productoDTO) throws ResourceNotFoundException, ResourceConflictException {
        return ResponseEntity.ok(productoService.actualizarProducto(productoDTO));
    }


    @Operation(summary = "Buscar producto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontró el producto con el id enviado.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró un producto con el id enviado.",
                    content = @Content)})
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(productoService.buscarProductoPorId(id));
    }


    @Operation(summary = "Filtrar productos por ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los productos de la ciudad con el id enviado correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró una ciudad con el id enviado o no se encontró ningún producto en la ciudad.",
                    content = @Content)})
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<Set<ProductoDTO>> filtrarPorCiudad(@PathVariable("id") Long ciudadId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productoService.filtrarProductosPorCiudad(ciudadId));
    }


    @Operation(summary = "Filtrar productos por categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los productos de la categoría con el id enviado correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró una categoría con el id enviado o no se encontró ningún producto de la categoría.",
                    content = @Content)})
    @GetMapping("/categoria/{id}")
    public ResponseEntity<Set<ProductoDTO>> filtrarPorCategoria(@PathVariable("id") Long categoriaId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productoService.filtrarProductosPorCategoria(categoriaId));
    }


    @Operation(summary = "Filtrar productos por fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los productos con disponibilidad entre las fechas correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún producto con disponibilidad entre las fechas enviadas.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "La fecha de inicio ingresada debe ser anterior a la fecha de fin",
                    content = @Content)})
    //antes /reservas
    @GetMapping("/fechas")
    public ResponseEntity<Set<ProductoDTO>> filtrarPorFechas(@Parameter(name = "fechaInicio", example = "2010-07-01") @RequestParam ("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @Parameter(name = "fechaFin", example = "2010-07-31") @RequestParam ("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) throws ResourceNotFoundException, ResourceConflictException {
        return ResponseEntity.ok(productoService.filtrarProductosPorFecha(fechaInicio, fechaFin));
    }


    @Operation(summary = "Filtrar productos por fechas y ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron los productos en la ciudad con el id enviado y disponibilidad entre las fechas correctamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún producto en la ciudad con el id enviado y disponibilidad entre las fechas enviadas.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "La fecha de inicio ingresada debe ser anterior a la fecha de fin",
                    content = @Content)})
    //antes /reservas/ciudad/{id}
    @GetMapping("/resultados/ciudad/{id}")
    public ResponseEntity<Set<ProductoDTO>> filtrarPorFechaYCiudad(@PathVariable("id") Long ciudadId, @RequestParam ("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @RequestParam ("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) throws ResourceNotFoundException, ResourceConflictException {
        return ResponseEntity.ok(productoService.filtrarPorFechaYCiudad(fechaInicio, fechaFin, ciudadId));
    }
}
