package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Descripcion;
import com.example.proyectoapiv1.model.dto.DescripcionDTO;
import com.example.proyectoapiv1.repository.DescripcionRepository;
import com.example.proyectoapiv1.service.IDescripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DescripcionService implements IDescripcionService {
    @Autowired
    DescripcionRepository descripcionRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(DescripcionService.class);

    @Override
    public Descripcion agregarDescripcion(DescripcionDTO descripcionDTO) throws ResourceConflictException {
        if(descripcionDTO.getTitulo()==null || descripcionDTO.getCuerpo()==null)
            throw  new ResourceConflictException("Intenta crear una descripción incompleta. Todos los datos son requeridos");
        Descripcion descripcion = mapper.convertValue(descripcionDTO, Descripcion.class);
        logger.info("La descripción se agregó correctamente");
        return descripcionRepository.save(descripcion);
    }

    @Override
    public Set<DescripcionDTO> listarDescripciones() {
        List<Descripcion> descripciones = descripcionRepository.findAll();
        Set<DescripcionDTO> descripcionesDTO = new HashSet<>();

        for(Descripcion descripcion : descripciones) {
            descripcionesDTO.add(mapper.convertValue(descripcion, DescripcionDTO.class));
        }
        logger.info("Las descripciones se listaron correctamente");
        return descripcionesDTO;
    }
}
