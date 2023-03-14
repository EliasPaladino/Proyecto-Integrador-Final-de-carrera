package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Producto;
import com.example.proyectoapiv1.model.Reserva;
import com.example.proyectoapiv1.model.Usuario;
import com.example.proyectoapiv1.model.dto.ProductoDTO;
import com.example.proyectoapiv1.model.dto.ReservaDTO;
import com.example.proyectoapiv1.model.dto.UsuarioDTO;
import com.example.proyectoapiv1.repository.ProductoRepository;
import com.example.proyectoapiv1.repository.ReservaRepository;
import com.example.proyectoapiv1.service.IReservaService;
import com.example.proyectoapiv1.service.Impl.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReservaService implements IReservaService {
    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UserDetailsServiceImpl usuarioService;

    @Autowired
    EnviarEmailService emailService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(ReservaService.class);

    @Override
    public Reserva agregarReserva(ReservaDTO reservaDTO) throws ResourceConflictException{
        if(reservaDTO.getFechaInicio()==null || reservaDTO.getFechaFin()==null || reservaDTO.getHoraIngreso()==null || reservaDTO.getUsuario()==null || reservaDTO.getProducto()==null)
            throw  new ResourceConflictException("Intenta crear una reserva incompleta. Todos los datos son requeridos");
        if(reservaDTO.getFechaInicio().isAfter(reservaDTO.getFechaFin()))
            throw  new ResourceConflictException("La fecha de inicio de reserva debe ser anterior a la fecha de fin de reserva");
        Reserva reserva = mapper.convertValue(reservaDTO, Reserva.class);
        logger.info("La reserva se agregó correctamente");
        return reservaRepository.save(reserva);
    }

    public void enviarMailDeReserva (Long id) throws ResourceNotFoundException, MessagingException {
        ReservaDTO reservaDTO = buscarReservaPorId(id);
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(reservaDTO.getUsuario().getId());
        Optional<Producto> producto = productoRepository.findById(reservaDTO.getProducto().getId());
        if (!producto.isPresent()){
            throw new ResourceNotFoundException("No se encontró un producto con id "+reservaDTO.getProducto().getId());
        }
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", usuarioDTO.getNombre());
        templateModel.put("productName", producto.get().getTitulo());
        templateModel.put("dayIn", reservaDTO.getFechaInicio());
        templateModel.put("dayOut", reservaDTO.getFechaFin());
        templateModel.put("checkIn", reservaDTO.getHoraIngreso());
        templateModel.put("comments", reservaDTO.getComentario());
        emailService.enviarTemplateReserva(usuarioDTO.getEmail(), "Confirmación de reserva | Digital Booking", templateModel);
    }

    @Override
    public Set<ReservaDTO> listarReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        Set<ReservaDTO> reservasDTO = new HashSet<>();

        for(Reserva reserva : reservas) {
            reservasDTO.add(mapper.convertValue(reserva, ReservaDTO.class));
        }

        logger.info("Las reservas se listaron correctamente");
        return reservasDTO;
    }

    @Override
    public Set<ReservaDTO> listarReservasPorProducto(Long id) throws ResourceNotFoundException {
        Optional<Producto> productoEncontrado = productoRepository.findById(id);
        if(!productoEncontrado.isPresent()){
            throw new ResourceNotFoundException("No existe un producto con id " + id);
        }
        Set<Reserva> reservasPorProducto = reservaRepository.getReservaByProductoId(id);
        Set<ReservaDTO> reservasPorProductoDTO = new HashSet<>();

        for(Reserva reserva : reservasPorProducto){
            reservasPorProductoDTO.add(mapper.convertValue(reserva, ReservaDTO.class));
        }

        logger.info("Las reservas del producto con id " + id + " se listaron correctamente");
        return reservasPorProductoDTO;
    }

    @Override
    public Set<ReservaDTO> listarReservasPorUsuario(Long id) throws ResourceNotFoundException {
        UsuarioDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        if(usuarioEncontrado==null){
            throw new ResourceNotFoundException("No existe un usuario con id " + id);
        }

        Set<Reserva> reservasDelUsuario = reservaRepository.getReservaByUsuarioId(id);
        Set<ReservaDTO> reservasDelUsuarioDTO = new HashSet<>();

        for (Reserva reserva: reservasDelUsuario) {
            reservasDelUsuarioDTO.add(mapper.convertValue(reserva, ReservaDTO.class));
        }

        logger.info("Las reservas del usuario con id " + id + " se listaron correctamente");
        return reservasDelUsuarioDTO;
    }

    @Override
    public Set<ReservaDTO> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) throws ResourceConflictException {
        if(fechaInicio.isAfter(fechaFin))
            throw  new ResourceConflictException("La fecha de inicio de reserva debe ser anterior a la fecha de fin de reserva");

        List<Reserva> reservasTodas = reservaRepository.findAll();

        Set<ReservaDTO> reservasNoDisponibles = new HashSet<>();
        for (Reserva reserva : reservasTodas) {
            if (reserva.getFechaInicio().isBefore(fechaFin) && reserva.getFechaFin().isAfter(fechaInicio)) {
                reservasNoDisponibles.add(mapper.convertValue(reserva, ReservaDTO.class));
            }
        }
        return reservasNoDisponibles;
    }

    @Override
    public ReservaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        ReservaDTO reservaDTO;
        if (reserva.isPresent()) {
            reservaDTO = mapper.convertValue(reserva, ReservaDTO.class);
        }else{
            throw new ResourceNotFoundException("No se encontró una reserva con el id "+id);
        }
        return reservaDTO;
    }
}
