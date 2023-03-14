package com.example.proyectoapiv1.service.Impl;

import com.example.proyectoapiv1.exceptions.ResourceConflictException;
import com.example.proyectoapiv1.exceptions.ResourceNotFoundException;
import com.example.proyectoapiv1.model.*;
import com.example.proyectoapiv1.model.dto.*;
import com.example.proyectoapiv1.repository.*;
import com.example.proyectoapiv1.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    CaracteristicaRepository caracteristicaRepository;

    @Autowired
    PoliticaRepository politicaRepository;

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    ReservaService reservaService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private static final Logger logger = Logger.getLogger(ProductoService.class);

    @Override
    public void agregarProducto(ProductoDTO productoDTO) throws ResourceConflictException {
        if(productoDTO.getTitulo()==null || productoDTO.getCategoria()==null || productoDTO.getUbicacion()==null || productoDTO.getImagenes() == null || productoDTO.getImagenes().isEmpty() || productoDTO.getCaracteristicas() == null || productoDTO.getCaracteristicas().isEmpty() || productoDTO.getDescripcion()==null || productoDTO.getPoliticas() == null || productoDTO.getPoliticas().isEmpty() || productoDTO.getDireccion()==null || productoDTO.getDistanciaCentro()==null)
            throw  new ResourceConflictException("Intenta crear un producto incompleto. Todos los datos son requeridos");

        Producto producto = mapper.convertValue(productoDTO, Producto.class);

        productoRepository.save(producto);

        Set<ImagenDTO> imagenesDTO = productoDTO.getImagenes();

        for (ImagenDTO imagenDTO : imagenesDTO) {
            //transformamos la imagenDTO en una imagen Entity
            Imagen imagen = mapper.convertValue(imagenDTO, Imagen.class);
            //seteamos el producto en la imagen
            imagen.setProducto(producto);
            //guardamos la imagen con el repository ya que estamos guardando una imagen Entity
            imagenRepository.save(imagen);
        }

        Set<CaracteristicaDTO> caracteristicasDTO = productoDTO.getCaracteristicas();

        for (CaracteristicaDTO caracteristicaDTO : caracteristicasDTO) {
            Caracteristica caracteristica = mapper.convertValue(caracteristicaDTO, Caracteristica.class);
            caracteristica.setProducto(producto);
            caracteristicaRepository.save(caracteristica);
        }

        Set<PoliticaDTO> politicasDTO = productoDTO.getPoliticas();

        for (PoliticaDTO politicaDTO : politicasDTO) {
            Politica politica = mapper.convertValue(politicaDTO, Politica.class);
            politica.setProducto(producto);
            politicaRepository.save(politica);
        }

        logger.info("El producto se agregó correctamente");
    }

    @Override
    public Set<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        //lista de productosDTO a retornar
        Set<ProductoDTO> productosDTO = new HashSet<>();

        for(Producto producto : productos) {
            ProductoDTO productoDTO = mapper.convertValue(producto, ProductoDTO.class);
            //por cada producto de nuestra base de datos debemos transformar sus listas de entity a DTO
            Set<Imagen> imagenes = producto.getImagenes();
            Set<ImagenDTO> imagenesDTO = new HashSet<>();
            for (Imagen imagen : imagenes) {
                //transformamos la imagen en una imagen DTO
                ImagenDTO imagenDTO = mapper.convertValue(imagen, ImagenDTO.class);
                imagenesDTO.add(imagenDTO);
            }
            productoDTO.setImagenes(imagenesDTO);

            Set<Caracteristica> caracteristicas = producto.getCaracteristicas();
            Set<CaracteristicaDTO> caracteristicasDTO = new HashSet<>();
            for (Caracteristica caracteristica : caracteristicas) {
                //transformamos la caracteristica en una caracteristica DTO
                CaracteristicaDTO caracteristicaDTO = mapper.convertValue(caracteristica, CaracteristicaDTO.class);
                caracteristicasDTO.add(caracteristicaDTO);
            }
            productoDTO.setCaracteristicas(caracteristicasDTO);

            Set<Politica> politicas = producto.getPoliticas();
            Set<PoliticaDTO> politicasDTO = new HashSet<>();
            for (Politica politica : politicas) {
                //transformamos la politica en una politica DTO
                PoliticaDTO politicaDTO = mapper.convertValue(politica, PoliticaDTO.class);
                politicasDTO.add(politicaDTO);
            }
            productoDTO.setPoliticas(politicasDTO);

            //agregamos al producto ya convertido en DTO a la lista de productosDTO a retornar
            productosDTO.add(productoDTO);
        }
        logger.info("Los productos se listaron correctamente");
        return productosDTO;
    }

    @Override
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) throws ResourceNotFoundException, ResourceConflictException {
        if(productoDTO.getId()==null){
            throw new ResourceConflictException("Se debe enviar el id del producto a actualizar");
        }
        ProductoDTO productoEncontrado = buscarProductoPorId(productoDTO.getId());
        if(productoEncontrado!=null){
            productoEncontrado.setTitulo(productoDTO.getTitulo());
            productoEncontrado.setCategoria(productoDTO.getCategoria());
            productoEncontrado.setUbicacion(productoDTO.getUbicacion());
            productoEncontrado.setImagenes(productoDTO.getImagenes());
            productoEncontrado.setDescripcion(productoDTO.getDescripcion());
            productoEncontrado.setCaracteristicas(productoDTO.getCaracteristicas());
            productoEncontrado.setPoliticas(productoDTO.getPoliticas());
            productoEncontrado.setLatitud(productoDTO.getLatitud());
            productoEncontrado.setLongitud(productoDTO.getLongitud());
            productoEncontrado.setDireccion(productoDTO.getDireccion());
            productoEncontrado.setDistanciaCentro(productoDTO.getDistanciaCentro());
            agregarProducto(productoEncontrado);

            logger.info("El producto con id " + productoDTO.getId() + " se actualizó correctamente");
            return productoEncontrado;

        }else{
            throw new ResourceNotFoundException("No se encontró un producto con id "+productoDTO.getId());
        }
    }

    @Override
    public ProductoDTO buscarProductoPorId(Long id) throws ResourceNotFoundException{
        ProductoDTO productoDTO;
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            productoDTO = mapper.convertValue(producto, ProductoDTO.class);

            //debemos transformar sus listas de entity a DTO
            Set<Imagen> imagenes = producto.get().getImagenes();
            Set<ImagenDTO> imagenesDTO = new HashSet<>();
            for (Imagen imagen : imagenes) {
                //transformamos la imagen en una imagen DTO
                ImagenDTO imagenDTO = mapper.convertValue(imagen, ImagenDTO.class);
                imagenesDTO.add(imagenDTO);
            }
            productoDTO.setImagenes(imagenesDTO);

            Set<Caracteristica> caracteristicas = producto.get().getCaracteristicas();
            Set<CaracteristicaDTO> caracteristicasDTO = new HashSet<>();
            for (Caracteristica caracteristica : caracteristicas) {
                //transformamos la caracteristica en una caracteristica DTO
                CaracteristicaDTO caracteristicaDTO = mapper.convertValue(caracteristica, CaracteristicaDTO.class);
                caracteristicasDTO.add(caracteristicaDTO);
            }
            productoDTO.setCaracteristicas(caracteristicasDTO);

            Set<Politica> politicas = producto.get().getPoliticas();
            Set<PoliticaDTO> politicasDTO = new HashSet<>();
            for (Politica politica : politicas) {
                //transformamos la politica en una politica DTO
                PoliticaDTO politicaDTO = mapper.convertValue(politica, PoliticaDTO.class);
                politicasDTO.add(politicaDTO);
            }
            productoDTO.setPoliticas(politicasDTO);

            return productoDTO;
        }
        else{
            throw new ResourceNotFoundException("No se encontró un producto con el id "+id);
        }

    }

    @Override
    public Set<ProductoDTO> filtrarProductosPorCiudad(Long ciudadId) throws ResourceNotFoundException{
        Optional<Ciudad> ciudadEncontrada = ciudadRepository.findById(ciudadId);

        if(!ciudadEncontrada.isPresent()){
            throw new ResourceNotFoundException("No existe una ciudad con id "+ ciudadId);
        }

        Set<ProductoDTO> productosDTO = listarProductos();
        Set<ProductoDTO> productosEnCiudad = new HashSet<>();

        for (ProductoDTO productoDTO : productosDTO) {
            if(productoDTO.getUbicacion().getId().equals(ciudadId)){
                productosEnCiudad.add(productoDTO);
            }
        }

        if (productosEnCiudad.isEmpty()){
            throw new ResourceNotFoundException("No se encontró ningún producto en la ciudad seleccionada");
        }else {
            logger.info("Se filtraron correctamente los productos de la ciudad con id "+ ciudadId);
            return productosEnCiudad;
        }
    }

    @Override
    public Set<ProductoDTO> filtrarProductosPorCategoria(Long categoriaId) throws ResourceNotFoundException {
        Set<ProductoDTO> productosDTO = listarProductos();
        Set<ProductoDTO> productosEnCategoria = new HashSet<>();

        for (ProductoDTO productoDTO : productosDTO) {
            if(productoDTO.getCategoria().getId().equals(categoriaId)){
                productosEnCategoria.add(productoDTO);
            }
        }

        if (productosEnCategoria.isEmpty()){
            throw new ResourceNotFoundException("No se encontró ningún producto en la categoría seleccionada");
        }else {
            logger.info("Se filtraron correctamente los productos de la categoría con id "+ categoriaId);
            return productosEnCategoria;
        }
    }

    @Override
    public Set<ProductoDTO> filtrarProductosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) throws ResourceNotFoundException, ResourceConflictException {
        if(fechaInicio.isAfter(fechaFin))
            throw  new ResourceConflictException("La fecha de inicio de reserva debe ser anterior a la fecha de fin de reserva");

        Set<ReservaDTO> reservasEntreFecha = reservaService.buscarPorFecha(fechaInicio, fechaFin);

        Set<Producto> productos = new HashSet<>();

        for (ReservaDTO reservaDTO : reservasEntreFecha) {
            Producto producto = reservaDTO.getProducto();
            productos.add(producto);
        }

        List<Producto> productosDisponibles = productoRepository.findAll();

        for (Producto producto: productos){
            productosDisponibles.removeIf(productoAChequear->productoAChequear.getId()==producto.getId());
        }

        Set<ProductoDTO> productosDisponiblesDTO = new HashSet<>();

        for (Producto producto: productosDisponibles){
            //productosDisponiblesDTO.add(mapper.convertValue(producto, ProductoDTO.class));
            ProductoDTO productoDisponibleDTO = mapper.convertValue(producto, ProductoDTO.class);

            Set<Imagen> imagenes = producto.getImagenes();
            Set<ImagenDTO> imagenesDTO = new HashSet<>();
            for (Imagen imagen : imagenes) {
                //transformamos la imagen en una imagen DTO
                ImagenDTO imagenDTO = mapper.convertValue(imagen, ImagenDTO.class);
                imagenesDTO.add(imagenDTO);
            }
            productoDisponibleDTO.setImagenes(imagenesDTO);

            Set<Caracteristica> caracteristicas = producto.getCaracteristicas();
            Set<CaracteristicaDTO> caracteristicasDTO = new HashSet<>();
            for (Caracteristica caracteristica : caracteristicas) {
                //transformamos la caracteristica en una caracteristica DTO
                CaracteristicaDTO caracteristicaDTO = mapper.convertValue(caracteristica, CaracteristicaDTO.class);
                caracteristicasDTO.add(caracteristicaDTO);
            }
            productoDisponibleDTO.setCaracteristicas(caracteristicasDTO);

            Set<Politica> politicas = producto.getPoliticas();
            Set<PoliticaDTO> politicasDTO = new HashSet<>();
            for (Politica politica : politicas) {
                //transformamos la politica en una politica DTO
                PoliticaDTO politicaDTO = mapper.convertValue(politica, PoliticaDTO.class);
                politicasDTO.add(politicaDTO);
            }
            productoDisponibleDTO.setPoliticas(politicasDTO);

            productosDisponiblesDTO.add(productoDisponibleDTO);
        }


        if (productosDisponiblesDTO.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró ningún producto cuya reserva este entre estas fechas");
        } else {
            logger.info("Se filtraron correctamente los productos que tienen reserva entre las fechas " + fechaInicio + " y " + fechaFin);
            return productosDisponiblesDTO;
        }
    }

    @Override
    public Set<ProductoDTO> filtrarPorFechaYCiudad(LocalDate fechaInicio, LocalDate fechaFin, Long ciudadId) throws ResourceNotFoundException, ResourceConflictException {

        Set<ProductoDTO> productosPorCiudadDTO = filtrarProductosPorCiudad(ciudadId);

        Set<ReservaDTO> reservasPorFechaDTO = reservaService.buscarPorFecha(fechaInicio, fechaFin);

        for (ReservaDTO reservaDTO : reservasPorFechaDTO) {
            productosPorCiudadDTO.removeIf(producto -> producto.getId() == reservaDTO.getProducto().getId());
        }

        if (productosPorCiudadDTO.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró ningún producto con reserva disponible en la ciudad indicada");
        } else {
            logger.info("Se filtraron correctamente los productos de la ciudad con id " + ciudadId + " con fechas disponibles entre " + fechaInicio + " y " + fechaFin);
            return productosPorCiudadDTO;
        }

    }

}
