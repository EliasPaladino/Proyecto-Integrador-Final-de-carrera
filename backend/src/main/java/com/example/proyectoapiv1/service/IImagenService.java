package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Imagen;
import com.example.proyectoapiv1.model.dto.ImagenDTO;

import java.util.Set;

public interface IImagenService {
    Imagen agregarImagen(ImagenDTO imagenDTO) throws ResourceConflictException;
    Set<ImagenDTO> listarImagenes();
}
