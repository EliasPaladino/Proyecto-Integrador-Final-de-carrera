package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Caracteristica;
import com.example.proyectoapiv1.model.dto.CaracteristicaDTO;
import com.example.proyectoapiv1.repository.CaracteristicaRepository;
import com.example.proyectoapiv1.service.ICaracteristicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

@Service
public class CaracteristicaService implements ICaracteristicaService {
    @Autowired
    CaracteristicaRepository caracteristicaRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(CaracteristicaService.class);

    @Override
    public Caracteristica agregarCaracteristica(CaracteristicaDTO caracteristicaDTO) throws ResourceConflictException {
        if(caracteristicaDTO.getNombre()==null || caracteristicaDTO.getIcono() == null)
            throw  new ResourceConflictException("Intenta crear una caracteristica incompleta. Todos los datos son requeridos");
        Caracteristica caracteristica = mapper.convertValue(caracteristicaDTO, Caracteristica.class);
        logger.info("La caracteristica se agregó correctamente");
        return caracteristicaRepository.save(caracteristica);
    }

    @Override
    public Set<CaracteristicaDTO> listarCaracteristicasNoRepetidas() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        List<Caracteristica> caracteristicasNoRepetidas = new ArrayList<>();
        Set<CaracteristicaDTO> caracteristicasDTO = new HashSet<>();


        for (Caracteristica caracteristicaAComparar : caracteristicas) {
            Integer bandera = 1;
            if(caracteristicasNoRepetidas.isEmpty()){
                caracteristicasNoRepetidas.add(caracteristicaAComparar);
            }else{
                for (int i = 0; i < caracteristicasNoRepetidas.size(); i++) {
                    if(caracteristicaAComparar.getNombre().equals(caracteristicasNoRepetidas.get(i).getNombre())){
                        bandera=0;
                    }
                }

                if(bandera==1){
                    caracteristicasNoRepetidas.add(caracteristicaAComparar);
                }
            }
        }


        for(Caracteristica caracteristica : caracteristicasNoRepetidas) {
            caracteristicasDTO.add(mapper.convertValue(caracteristica, CaracteristicaDTO.class));
        }


        logger.info("Las caracteristicas se listaron correctamente");
        return caracteristicasDTO;
    }
}
