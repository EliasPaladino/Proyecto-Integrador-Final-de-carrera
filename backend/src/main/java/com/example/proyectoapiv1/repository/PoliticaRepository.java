package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Politica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticaRepository extends JpaRepository<Politica, Long> {
}
