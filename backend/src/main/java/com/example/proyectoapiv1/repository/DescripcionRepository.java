package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Descripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescripcionRepository extends JpaRepository<Descripcion, Long> {
}
