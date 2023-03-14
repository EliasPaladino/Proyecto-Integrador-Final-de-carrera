package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("select r from Reserva r where r.producto.id =:productoId")
    Set<Reserva> getReservaByProductoId(@Param("productoId") Long id);

    @Query("select r from Reserva r where r.usuario.id =:usuarioId")
    Set<Reserva> getReservaByUsuarioId(@Param("usuarioId") Long id);

}
