package com.example.proyectoapiv1.service.Impl.security;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Categoria;
import com.example.proyectoapiv1.model.Rol;
import com.example.proyectoapiv1.model.Usuario;
import com.example.proyectoapiv1.model.dto.CategoriaDTO;
import com.example.proyectoapiv1.model.dto.RolDTO;
import com.example.proyectoapiv1.model.dto.UsuarioDTO;
import com.example.proyectoapiv1.repository.RolRepository;
import com.example.proyectoapiv1.repository.UsuarioRepository;
import com.example.proyectoapiv1.service.IUsuarioService;
import com.example.proyectoapiv1.service.Impl.EnviarEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EnviarEmailService emailService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);


    //metodo de inicio de sesion
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //buscamos el usuario
        Optional<Usuario> usuario = usuarioRepository.getUserByEmail(email);

        if (usuario.isPresent()) {
            UsuarioDTO usuarioDTO = mapper.convertValue(usuario, UsuarioDTO.class);

            Set<GrantedAuthority> autorizaciones = new HashSet<>();
            GrantedAuthority autorizacion = new SimpleGrantedAuthority(usuarioDTO.getRol().getNombre());
            autorizaciones.add(autorizacion);

            //armamos el usuario de tipo "User" (clase de spring)
            User userDetail = new User(usuarioDTO.getEmail(), usuarioDTO.getContrasenia(), true, true, true, true, autorizaciones);

            logger.info("Se inició sesión correctamente");
            //retornamos el usuario que necesita spring para almacenar
            return userDetail;
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    @Override
    public Usuario agregarUsuario(UsuarioDTO usuarioDTO) throws ResourceConflictException, MessagingException {
        if(usuarioDTO.getNombre()==null || usuarioDTO.getApellido()==null || usuarioDTO.getEmail()==null || usuarioDTO.getContrasenia()==null || usuarioDTO.getCiudad()==null)
            throw  new ResourceConflictException("Intenta crear un usuario incompleto. Todos los datos son requeridos");
        //vemos si encontramos un usuario con el mismo email que el inresado en el formulario de registro
        Optional<Usuario> usuarioEncontrado = usuarioRepository.getUserByEmail(usuarioDTO.getEmail());

        if(usuarioEncontrado.isPresent()){
            throw new ResourceConflictException("El email ingresado ya fue registrado");
        }else{
            Optional<Rol> rolDefault = rolRepository.getRolByName("ROLE_USER");
            if(usuarioDTO.getRol()==null && rolDefault.isPresent()){
                usuarioDTO.setRol(mapper.convertValue(rolDefault.get(), RolDTO.class));
            }

            Usuario usuario = new Usuario(usuarioDTO.getNombre(), usuarioDTO.getApellido(), usuarioDTO.getEmail(), new BCryptPasswordEncoder().encode(usuarioDTO.getContrasenia()), usuarioDTO.getCiudad(), mapper.convertValue(usuarioDTO.getRol(), Rol.class));

            logger.info("El usuario se agregó correctamente");

            //enviamos el mail de confirmacion de registro
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("recipientName", usuario.getNombre());
            emailService.enviarTemplateRegistro(usuario.getEmail(), "Confirmación de registro en Digital Booking", templateModel);

            return usuarioRepository.save(usuario);
        }
    }

    @Override
    public Set<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Set<UsuarioDTO> usuarioDTO = new HashSet<>();

        for(Usuario usuario : usuarios) {
            usuarioDTO.add(mapper.convertValue(usuario, UsuarioDTO.class));
        }
        logger.info("Los usuarios se listaron correctamente");
        return usuarioDTO;
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String email) throws ResourceNotFoundException {
        UsuarioDTO usuarioDTO;
        Optional<Usuario> usuario = usuarioRepository.getUserByEmail(email);

        if(usuario.isPresent()){
            usuarioDTO = mapper.convertValue(usuario, UsuarioDTO.class);
        }else {
            throw new ResourceNotFoundException("No se encontró un usuario con email "+email);
        }

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) throws ResourceNotFoundException{
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        UsuarioDTO usuarioDTO;
        if (usuario.isPresent()) {
            usuarioDTO = mapper.convertValue(usuario, UsuarioDTO.class);
        }else{
            throw new ResourceNotFoundException("No se encontró un usuario con el id "+id);
        }
        return usuarioDTO;
    }
}
