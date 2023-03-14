package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;

import com.example.proyectoapiv1.model.Politica;
import com.example.proyectoapiv1.model.dto.PoliticaDTO;

import com.example.proyectoapiv1.repository.PoliticaRepository;
import com.example.proyectoapiv1.service.IPoliticaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PoliticaService implements IPoliticaService {
    @Autowired
    PoliticaRepository politicaRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(PoliticaService.class);

    @Override
    public Politica agregarPolitica(PoliticaDTO politicaDTO) throws ResourceConflictException {
        if(politicaDTO.getTipoPolitica()==null || politicaDTO.getPolitica()==null)
            throw  new ResourceConflictException("Intenta crear una política incompleta. Todos los datos son requeridos");
        Politica politica = mapper.convertValue(politicaDTO, Politica.class);
        logger.info("La política se agregó correctamente");
        return politicaRepository.save(politica);
    }

    @Override
    public Set<PoliticaDTO> listarPoliticas() {
        List<Politica> politicas = politicaRepository.findAll();
        Set<PoliticaDTO> politicasDTO = new HashSet<>();

        for(Politica politica : politicas) {
            politicasDTO.add(mapper.convertValue(politica, PoliticaDTO.class));
        }
        logger.info("Las políticas se listaron correctamente");
        return politicasDTO;
    }
}
