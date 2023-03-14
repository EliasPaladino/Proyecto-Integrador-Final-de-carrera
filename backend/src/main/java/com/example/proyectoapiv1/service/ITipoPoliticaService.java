package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.TipoPolitica;
import com.example.proyectoapiv1.model.dto.TipoPoliticaDTO;

import java.util.Set;

public interface ITipoPoliticaService {
    TipoPolitica agregarTipoPolitica(TipoPoliticaDTO tipoPoliticaDTO) throws ResourceConflictException;
    Set<TipoPoliticaDTO> listarTiposPolitica();
}
