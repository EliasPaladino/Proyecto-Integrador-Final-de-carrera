package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Categoria;
import com.example.proyectoapiv1.model.dto.CategoriaDTO;

import java.util.Set;

public interface ICategoriaService {
    Categoria agregarCategoria(CategoriaDTO categoriaDTO) throws ResourceConflictException;
    Set<CategoriaDTO> listarCategorias();
    Categoria editarCategoria(CategoriaDTO categoriaDTO) throws ResourceNotFoundException, ResourceConflictException;
    CategoriaDTO buscarCategoriaPorId(Long id) throws ResourceNotFoundException;
    void eliminarCategoria(Long id) throws BadRequestException, ResourceNotFoundException;
}
