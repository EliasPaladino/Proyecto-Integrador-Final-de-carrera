package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.email =:email")
    Optional<Usuario> getUserByEmail(@Param("email") String email);
}
