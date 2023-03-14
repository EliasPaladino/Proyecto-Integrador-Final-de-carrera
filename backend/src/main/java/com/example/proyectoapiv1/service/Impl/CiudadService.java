package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Ciudad;
import com.example.proyectoapiv1.model.dto.CiudadDTO;
import com.example.proyectoapiv1.repository.CiudadRepository;
import com.example.proyectoapiv1.service.ICiudadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CiudadService implements ICiudadService {
    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(CiudadService.class);

    @Override
    public Ciudad agregarCiudad(CiudadDTO ciudadDTO) throws ResourceConflictException {
        if(ciudadDTO.getNombre()==null || ciudadDTO.getPais()==null)
            throw  new ResourceConflictException("Intenta crear una ciudad incompleta. Todos los datos son requeridos");
        Ciudad ciudad = mapper.convertValue(ciudadDTO, Ciudad.class);
        logger.info("La ciudad se agregó correctamente");
        return ciudadRepository.save(ciudad);
    }

    @Override
    public Set<CiudadDTO> listarCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        Set<CiudadDTO> ciudadesDTO = new HashSet<>();

        for(Ciudad ciudad : ciudades) {
            ciudadesDTO.add(mapper.convertValue(ciudad, CiudadDTO.class));
        }
        logger.info("Las ciudades se listaron correctamente");
        return ciudadesDTO;
    }

    @Override
    public CiudadDTO buscarCiudadPorId(Long id) throws ResourceNotFoundException {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        CiudadDTO ciudadDTO;
        if (ciudad.isPresent()) {
            ciudadDTO = mapper.convertValue(ciudad, CiudadDTO.class);
        }else{
            throw new ResourceNotFoundException("No se encontró una ciudad con el id "+id);
        }
        return ciudadDTO;
    }
}
