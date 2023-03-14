package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;

import com.example.proyectoapiv1.model.dto.ProductoDTO;

import java.time.LocalDate;
import java.util.Set;

public interface IProductoService {
    void agregarProducto(ProductoDTO productoDTO) throws ResourceConflictException;
    Set<ProductoDTO> listarProductos();
    ProductoDTO actualizarProducto(ProductoDTO productoDTO) throws ResourceNotFoundException, ResourceConflictException;
    ProductoDTO buscarProductoPorId(Long id) throws ResourceNotFoundException;
    Set<ProductoDTO> filtrarProductosPorCiudad(Long ciudadId) throws ResourceNotFoundException;
    Set<ProductoDTO> filtrarProductosPorCategoria(Long categoriaId) throws ResourceNotFoundException;
    Set<ProductoDTO> filtrarProductosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) throws ResourceNotFoundException, ResourceConflictException;
    Set<ProductoDTO> filtrarPorFechaYCiudad(LocalDate fechaInicio, LocalDate fechaFin, Long ciudadId) throws ResourceNotFoundException, ResourceConflictException;

}