package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Descripcion;
import com.example.proyectoapiv1.model.dto.DescripcionDTO;

import java.util.Set;

public interface IDescripcionService {
    Descripcion agregarDescripcion(DescripcionDTO descripcionDTO) throws ResourceConflictException;
    Set<DescripcionDTO> listarDescripciones();
}
