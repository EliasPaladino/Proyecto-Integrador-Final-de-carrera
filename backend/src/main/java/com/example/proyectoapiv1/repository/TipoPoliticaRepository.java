package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.TipoPolitica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPoliticaRepository extends JpaRepository<TipoPolitica, Long> {
}
