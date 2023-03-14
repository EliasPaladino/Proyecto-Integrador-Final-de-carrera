package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Puntuacion;
import com.example.proyectoapiv1.model.dto.PuntuacionDTO;
import com.example.proyectoapiv1.repository.PuntuacionRepository;
import com.example.proyectoapiv1.service.IPuntuacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PuntuacionService implements IPuntuacionService {
    @Autowired
    PuntuacionRepository puntuacionRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(PuntuacionService.class);

    @Override
    public Puntuacion agregarPuntuacion(PuntuacionDTO puntuacionDTO) throws ResourceConflictException {
        if(puntuacionDTO.getPuntuacion()==null || puntuacionDTO.getProducto()==null || puntuacionDTO.getUsuario()==null)
            throw  new ResourceConflictException("Intenta crear una puntuación incompleta. Todos los datos son requeridos");
        Puntuacion puntuacion = mapper.convertValue(puntuacionDTO, Puntuacion.class);
        logger.info("La puntuación se agregó correctamente");
        return puntuacionRepository.save(puntuacion);
    }

    @Override
    public Set<PuntuacionDTO> listarPuntuaciones() {
        List<Puntuacion> puntuaciones= puntuacionRepository.findAll();
        Set<PuntuacionDTO> puntuacionesDTO= new HashSet<>();

        for(Puntuacion puntuacion:puntuaciones){
            puntuacionesDTO.add(mapper.convertValue(puntuacion, PuntuacionDTO.class));
        }

        logger.info("Las puntuaciones se listaron correctamente");
        return puntuacionesDTO;
    }
}

