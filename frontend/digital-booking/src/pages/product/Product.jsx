import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";

import ProductHeader from "./productHeader/ProductHeader";
import ProductLocation from "./productLocation/ProductLocation";
import Description from "./description/Description";
import Features from "./features/Features";
import ProductDatePicker from "./date-range-picker/Date-picker";
import Politics from "./politics/Politics";
import CarouselMob from "./gallery/CarouselMob";
import Gallery from "./gallery/Gallery";
import CarouselDesktop from "./gallery/CarouselDesktop";
import Map from "./map/Map";
import ProductShare from "./share/share";
import urls from "../../enum/api-urls";
import httpClient from "../../security/interceptor";

export default function Product() {
  const { id } = useParams();
  const [datos, setDatos] = useState({});

  const getData = async () => {
    const response = await httpClient(`${urls.productos}buscar/${id}`);
    const data = await response.data;
    setDatos(data);
  }

  useEffect(() => {
    getData();
    window.scrollTo({top: 0, left: 0, behavior: 'smooth'});
  }, []);

  const [carouselActivo, setCarouselActivo] = useState(false);

  function renderCarousel() {
    setCarouselActivo(!carouselActivo);
  }


  return (
    <>
      <Header />
    {datos.categoria && <ProductHeader categoria={datos.categoria.titulo} titulo={datos.titulo}/>}
      {datos.ubicacion && <ProductLocation ciudad={datos.ubicacion.nombre} pais={datos.ubicacion.pais} distancia={datos.distanciaCentro}/>}
      <ProductShare />
      {datos.imagenes && <Gallery renderCarousel={renderCarousel} imagenes={datos.imagenes}/>}
      
      {datos.imagenes && <CarouselMob imagenes={ datos.imagenes }/>}
      {datos.descripcion && <Description cuerpo={datos.descripcion.cuerpo} titulo={datos.descripcion.titulo}/>}
      {datos.caracteristicas && <Features caracteristicas={datos.caracteristicas} />}
      
      <ProductDatePicker title={"Fechas disponibles"} showInit={true}/>
      {datos.ubicacion && <Map ciudad={datos.ubicacion.nombre} pais={datos.ubicacion.pais} lat={datos.latitud} long={datos.longitud} titulo={datos.titulo}/>}
      {datos.politicas && <Politics politicas={datos.politicas} />}
      {carouselActivo ? (
        datos.imagenes && <CarouselDesktop
          carouselActivo={carouselActivo}
          renderCarousel={renderCarousel}
          imagenes={datos.imagenes}
        />
      ) : null}
      <Footer />
      </>
  );
}
