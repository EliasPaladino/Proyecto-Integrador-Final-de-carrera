package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Favorito;
import com.example.proyectoapiv1.model.Producto;
import com.example.proyectoapiv1.model.Usuario;
import com.example.proyectoapiv1.model.dto.FavoritoDTO;
import com.example.proyectoapiv1.repository.FavoritoRepository;
import com.example.proyectoapiv1.repository.ProductoRepository;
import com.example.proyectoapiv1.repository.UsuarioRepository;
import com.example.proyectoapiv1.service.IFavoritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FavoritoService implements IFavoritoService {

    @Autowired
    FavoritoRepository favoritoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(FavoritoService.class);

    @Override
    public Favorito agregarFavorito(FavoritoDTO favoritoDTO) throws ResourceConflictException, ResourceNotFoundException {
        if(favoritoDTO.getUsuario()==null || favoritoDTO.getProducto()==null)
            throw  new ResourceConflictException("Intenta crear un favorito incompleto. Todos los datos son requeridos");
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(favoritoDTO.getUsuario().getId());
        if(usuarioEncontrado.isPresent()){
            Favorito favorito = mapper.convertValue(favoritoDTO, Favorito.class);
            logger.info("El favorito se agregó correctamente");
            return favoritoRepository.save(favorito);
        }else {
            throw new ResourceNotFoundException("No se encontró un usuario con id "+favoritoDTO.getUsuario().getId());
        }
    }

    @Override
    public Set<FavoritoDTO> listarFavoritos() {
        List<Favorito> favoritos= favoritoRepository.findAll();
        Set<FavoritoDTO> favoritosDTO= new HashSet<>();

        for(Favorito favorito : favoritos){
            favoritosDTO.add(mapper.convertValue(favorito, FavoritoDTO.class));
        }

        logger.info("Los favoritos se listaron correctamente");
        return favoritosDTO;
    }

    @Override
    public Set<FavoritoDTO> listarFavoritosPorUsuario(Long id) throws ResourceNotFoundException{
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);
        if(usuarioEncontrado.isPresent()){
            Set<Favorito> favoritosPorUsuario = favoritoRepository.getFavoritoByUsuarioId(id);
            Set<FavoritoDTO> favoritosPorUsuarioDTO = new HashSet<>();

            for(Favorito favorito : favoritosPorUsuario){
                favoritosPorUsuarioDTO.add(mapper.convertValue(favorito, FavoritoDTO.class));
            }

            logger.info("Los favoritos del ususario con id " + id +" se listaron correctamente");
            return favoritosPorUsuarioDTO;
        }else{
            throw new ResourceNotFoundException("No se encontró un usuario con id "+id);
        }
    }

    /*@Override
    public Boolean eliminarFavoritoPorUsuarioYProducto(Long idUsuario, Long idProducto) throws BadRequestException, ResourceNotFoundException {
        Boolean eliminado = false;
        if (idUsuario<1)
            throw new BadRequestException("El id del usuario no puede ser negativo");
        if (idProducto<1)
            throw new BadRequestException("El id del producto no puede ser negativo");
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(idUsuario);
        Optional<Producto> productoEncontrado = productoRepository.findById(idProducto);
        if(usuarioEncontrado.isPresent()){
            if(productoEncontrado.isPresent()) {
                Set<Favorito> favoritosPorUsuarioYProducto = favoritoRepository.getFavoritoByUsuarioIdAndProductoId(idUsuario, idProducto);
                //agregado por celeste
                if(favoritosPorUsuarioYProducto.isEmpty()){
                    throw new ResourceNotFoundException("El usuario con id "+idUsuario + " no selecciono al producto con id: " + idProducto + " como favorito.");
                } else {
                    for (Favorito favorito : favoritosPorUsuarioYProducto) {
                        favoritoRepository.delete(favorito);
                        eliminado = true;
                    }
                    logger.info("El favorito se eliminó correctamente");
                }
            }else {
                throw new ResourceNotFoundException("No se encontró un producto con id "+ idProducto);
            }
        }else {
            throw new ResourceNotFoundException("No se encontró un usuario con id "+idUsuario);
        }

        return eliminado;

    }
*/

    @Override
    public void eliminarFavoritoPorUsuarioYProducto(Long idUsuario, Long idProducto) throws BadRequestException, ResourceNotFoundException {
        if (idUsuario<1)
            throw new BadRequestException("El id del usuario no puede ser negativo");
        if (idProducto<1)
            throw new BadRequestException("El id del producto no puede ser negativo");
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(idUsuario);
        Optional<Producto> productoEncontrado = productoRepository.findById(idProducto);
        if(usuarioEncontrado.isPresent()){
            if(productoEncontrado.isPresent()) {
                Set<Favorito> favoritosPorUsuarioYProducto = favoritoRepository.getFavoritoByUsuarioIdAndProductoId(idUsuario, idProducto);
                if(favoritosPorUsuarioYProducto.isEmpty()){
                    throw new ResourceNotFoundException("El usuario con id "+idUsuario + " no selecciono al producto con id " + idProducto + " como favorito.");
                } else {
                    for (Favorito favorito : favoritosPorUsuarioYProducto) {
                        favoritoRepository.delete(favorito);
                    }
                    logger.info("El favorito se eliminó correctamente");
                }
            }else {
                throw new ResourceNotFoundException("No se encontró un producto con id "+idProducto);
            }
        }else {
            throw new ResourceNotFoundException("No se encontró un usuario con id "+idUsuario);
        }


    }

}
