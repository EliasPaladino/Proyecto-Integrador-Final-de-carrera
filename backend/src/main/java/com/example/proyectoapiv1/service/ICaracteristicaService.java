package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Caracteristica;
import com.example.proyectoapiv1.model.dto.CaracteristicaDTO;

import java.util.Set;

public interface ICaracteristicaService {
    Caracteristica agregarCaracteristica(CaracteristicaDTO caracteristicaDTO) throws ResourceConflictException;
    Set<CaracteristicaDTO> listarCaracteristicasNoRepetidas();
}
