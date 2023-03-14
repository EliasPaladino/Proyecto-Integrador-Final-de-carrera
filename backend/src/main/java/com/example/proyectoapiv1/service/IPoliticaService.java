package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Politica;
import com.example.proyectoapiv1.model.dto.PoliticaDTO;

import java.util.Set;

public interface IPoliticaService {
    Politica agregarPolitica(PoliticaDTO politicaDTO) throws ResourceConflictException;
    Set<PoliticaDTO> listarPoliticas();
}
