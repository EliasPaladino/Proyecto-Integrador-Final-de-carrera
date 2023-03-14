package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.model.Rol;
import com.example.proyectoapiv1.model.Usuario;
import com.example.proyectoapiv1.model.dto.RolDTO;
import com.example.proyectoapiv1.model.dto.UsuarioDTO;
import com.example.proyectoapiv1.repository.RolRepository;
import com.example.proyectoapiv1.service.IRolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RolService implements IRolService {
    @Autowired
    RolRepository rolRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(RolService.class);

    @Override
    public Rol agregarRol(RolDTO rolDTO) throws ResourceConflictException {
        if(rolDTO.getNombre()==null)
            throw  new ResourceConflictException("Intenta crear un rol incompleto. Todos los datos son requeridos");
        Rol rol = mapper.convertValue(rolDTO, Rol.class);
        /*rol.setNombre("ROLE_"+rol.getNombre());*/
        logger.info("El rol se agregó correctamente");
        return rolRepository.save(rol);
    }

    @Override
    public Set<RolDTO> listarRoles() {
        List<Rol> roles = rolRepository.findAll();
        Set<RolDTO> rolesDTO = new HashSet<>();

        for(Rol rol : roles) {
            rolesDTO.add(mapper.convertValue(rol, RolDTO.class));
        }
        logger.info("Los roles se listaron correctamente");
        return rolesDTO;
    }
}
