package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {
}
