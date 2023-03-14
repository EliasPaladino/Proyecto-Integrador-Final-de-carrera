package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Puntuacion;
import com.example.proyectoapiv1.model.dto.PuntuacionDTO;

import java.util.Set;

public interface IPuntuacionService {
    Puntuacion agregarPuntuacion(PuntuacionDTO puntuacionDTO) throws ResourceConflictException;
    Set<PuntuacionDTO> listarPuntuaciones();
}
