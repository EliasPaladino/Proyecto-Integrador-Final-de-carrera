package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Rol;
import com.example.proyectoapiv1.model.dto.RolDTO;

import java.util.Set;

public interface IRolService {
    Rol agregarRol(RolDTO rolDTO) throws ResourceConflictException;
    Set<RolDTO> listarRoles();
}
