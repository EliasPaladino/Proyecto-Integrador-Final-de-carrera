package com.example.proyectoapiv1.service;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Reserva;
import com.example.proyectoapiv1.model.dto.ReservaDTO;

import java.time.LocalDate;
import java.util.Set;

public interface IReservaService {
    Reserva agregarReserva(ReservaDTO reservaDTO) throws ResourceConflictException, ResourceNotFoundException;
    Set<ReservaDTO> listarReservas();
    Set<ReservaDTO> listarReservasPorProducto(Long id) throws ResourceNotFoundException;
    Set<ReservaDTO> listarReservasPorUsuario(Long id) throws ResourceNotFoundException;
    Set<ReservaDTO> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) throws ResourceConflictException;
    ReservaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException;
}
