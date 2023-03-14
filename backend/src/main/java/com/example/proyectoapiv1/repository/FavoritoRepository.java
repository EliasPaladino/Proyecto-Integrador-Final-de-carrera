package com.example.proyectoapiv1.repository;

import com.example.proyectoapiv1.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    @Query("select f from Favorito f where f.usuario.id =:usuarioId")
    Set<Favorito> getFavoritoByUsuarioId(@Param("usuarioId") Long id);

    @Query("select f from Favorito f where f.usuario.id =:usuarioId and f.producto.id =:productoId")
    Set<Favorito> getFavoritoByUsuarioIdAndProductoId(@Param("usuarioId") Long idUsuario, @Param("productoId") Long idProducto);
}
