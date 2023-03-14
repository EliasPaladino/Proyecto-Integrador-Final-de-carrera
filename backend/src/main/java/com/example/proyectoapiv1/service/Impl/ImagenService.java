package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Imagen;
import com.example.proyectoapiv1.model.dto.ImagenDTO;
import com.example.proyectoapiv1.repository.ImagenRepository;
import com.example.proyectoapiv1.service.IImagenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImagenService implements IImagenService {
    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(ImagenService.class);

    @Override
    public Imagen agregarImagen(ImagenDTO imagenDTO) throws ResourceConflictException {
        if(imagenDTO.getUrl()==null)
            throw  new ResourceConflictException("Intenta crear una imagen incompleta. Todos los datos son requeridos");
        Imagen imagen = mapper.convertValue(imagenDTO, Imagen.class);
        logger.info("La imagen se agregó correctamente");
        return imagenRepository.save(imagen);
    }

    @Override
    public Set<ImagenDTO> listarImagenes() {
        List<Imagen> imagenes = imagenRepository.findAll();
        Set<ImagenDTO> imagenesDTO = new HashSet<>();

        for(Imagen imagen : imagenes) {
            imagenesDTO.add(mapper.convertValue(imagen, ImagenDTO.class));
        }
        logger.info("Las imagenes se listaron correctamente");
        return imagenesDTO;
    }
}
