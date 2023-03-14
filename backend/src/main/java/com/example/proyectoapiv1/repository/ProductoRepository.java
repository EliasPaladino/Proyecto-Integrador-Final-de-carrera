package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Favorito;
import com.example.proyectoapiv1.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
