package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.TipoPolitica;
import com.example.proyectoapiv1.model.dto.TipoPoliticaDTO;
import com.example.proyectoapiv1.repository.TipoPoliticaRepository;
import com.example.proyectoapiv1.service.ITipoPoliticaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TipoPoliticaService implements ITipoPoliticaService {
    @Autowired
    TipoPoliticaRepository tipoPoliticaRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(TipoPoliticaService.class);

    @Override
    public TipoPolitica agregarTipoPolitica(TipoPoliticaDTO tipoPoliticaDTO) throws ResourceConflictException {
        if(tipoPoliticaDTO.getTipoPolitica()==null)
            throw  new ResourceConflictException("Intenta crear un tipo de politica incompleto. Todos los datos son requeridos");
        TipoPolitica tipoPolitica = mapper.convertValue(tipoPoliticaDTO, TipoPolitica.class);
        logger.info("El tipo de politica se agregó correctamente");
        return tipoPoliticaRepository.save(tipoPolitica);
    }

    @Override
    public Set<TipoPoliticaDTO> listarTiposPolitica() {
        List<TipoPolitica> tipoPoliticas = tipoPoliticaRepository.findAll();
        Set<TipoPoliticaDTO> tipoPoliticasDTO = new HashSet<>();

        for(TipoPolitica tipoPolitica : tipoPoliticas) {
            tipoPoliticasDTO.add(mapper.convertValue(tipoPolitica, TipoPoliticaDTO.class));
        }
        logger.info("Los tipos de politica se listaron correctamente");
        return tipoPoliticasDTO;
    }
}
