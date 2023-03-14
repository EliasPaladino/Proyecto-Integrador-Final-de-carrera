package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Usuario;
import com.example.proyectoapiv1.model.dto.ProductoDTO;
import com.example.proyectoapiv1.model.dto.UsuarioDTO;

import javax.mail.MessagingException;
import java.util.Set;

public interface IUsuarioService {
    Usuario agregarUsuario(UsuarioDTO usuarioDTO) throws ResourceConflictException, MessagingException;
    Set<UsuarioDTO> listarUsuarios();
    UsuarioDTO buscarUsuarioPorEmail(String email) throws ResourceNotFoundException;
    UsuarioDTO buscarUsuarioPorId(Long id) throws ResourceNotFoundException;
}
