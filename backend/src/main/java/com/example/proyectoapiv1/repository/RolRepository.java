package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("select r from Rol r where r.nombre =:nombre")
    Optional<Rol> getRolByName(@Param("nombre") String nombre);
}
