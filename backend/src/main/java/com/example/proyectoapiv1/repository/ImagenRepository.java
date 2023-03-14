package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,Long> {
}
