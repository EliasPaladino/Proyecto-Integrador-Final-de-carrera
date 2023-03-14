package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Favorito;
import com.example.proyectoapiv1.model.dto.FavoritoDTO;

import java.util.Set;

public interface IFavoritoService {
    Favorito agregarFavorito(FavoritoDTO favoritoDTO) throws ResourceConflictException, ResourceNotFoundException;
    Set<FavoritoDTO> listarFavoritos();
    Set<FavoritoDTO> listarFavoritosPorUsuario(Long id) throws ResourceNotFoundException;
    /*Boolean eliminarFavoritoPorUsuarioYProducto(Long idUsuario, Long idProducto) throws BadRequestException, ResourceNotFoundException;*/
    void eliminarFavoritoPorUsuarioYProducto(Long idUsuario, Long idProducto) throws BadRequestException, ResourceNotFoundException;
}
