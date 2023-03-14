package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Ciudad;
import com.example.proyectoapiv1.model.dto.CiudadDTO;


import java.util.Set;

public interface ICiudadService {
    Ciudad agregarCiudad(CiudadDTO ciudadDTO) throws ResourceConflictException;
    Set<CiudadDTO> listarCiudades();
    CiudadDTO buscarCiudadPorId(Long id) throws ResourceNotFoundException;
}
