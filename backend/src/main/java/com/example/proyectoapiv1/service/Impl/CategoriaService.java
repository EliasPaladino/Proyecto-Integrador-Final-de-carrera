package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.BadRequestException;
import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.Categoria;
import com.example.proyectoapiv1.model.dto.CategoriaDTO;
import com.example.proyectoapiv1.model.dto.ProductoDTO;
import com.example.proyectoapiv1.repository.CategoriaRepository;
import com.example.proyectoapiv1.service.ICategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoriaService implements ICategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    @Override
    public Categoria agregarCategoria(CategoriaDTO categoriaDTO) throws ResourceConflictException {
        if(categoriaDTO.getTitulo()==null || categoriaDTO.getDescripcion()==null || categoriaDTO.getUrlImagen()==null)
            throw  new ResourceConflictException("Intenta crear una categoría incompleta. Todos los datos son requeridos");
        Categoria categoria = mapper.convertValue(categoriaDTO, Categoria.class);
        logger.info("La categoría se agregó correctamente");
        return categoriaRepository.save(categoria);
    }


    @Override
    public Set<CategoriaDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        Set<CategoriaDTO> categoriaDTO = new HashSet<>();

        for(Categoria categoria : categorias) {
            categoriaDTO.add(mapper.convertValue(categoria, CategoriaDTO.class));
        }
        logger.info("Las categorías se listaron correctamente");
        return categoriaDTO;
    }

    @Override
    public Categoria editarCategoria(CategoriaDTO categoriaDTO) throws ResourceNotFoundException, ResourceConflictException {
        if(buscarCategoriaPorId(categoriaDTO.getId()) == null)
            throw  new ResourceNotFoundException("No existe la categoría con id: " + categoriaDTO.getId());
        logger.info("La categoría se actualizó correctamente");
        return agregarCategoria(categoriaDTO);
    }

    @Override
    public CategoriaDTO buscarCategoriaPorId(Long id) throws ResourceNotFoundException {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        CategoriaDTO categoriaDTO;
        if (categoria.isPresent()) {
            categoriaDTO = mapper.convertValue(categoria, CategoriaDTO.class);
        }else{
            throw new ResourceNotFoundException("No se encontró una categoría con el id "+id);
        }
        return categoriaDTO;
    }

    @Override
    public void eliminarCategoria(Long id) throws BadRequestException, ResourceNotFoundException{
        if (id<1)
            throw new BadRequestException("El id de la categoría no puede ser negativo");
        if (!categoriaRepository.existsById(id))
            throw  new ResourceNotFoundException("No existe la categoría con id: " + id);
        logger.info("La categoría con id: " + id + " se eliminó correctamente");
        categoriaRepository.deleteById(id);
    }


}