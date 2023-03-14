import React, { useEffect, useState } from "react";
import "./carousel.css";

/* Hardcodeo imagenes de prueba */
import galeria1 from "../../../assets/img/producto/gallery/galeria1.png";
import galeria2 from "../../../assets/img/producto/gallery/galeria2.png";
import galeria3 from "../../../assets/img/producto/gallery/galeria3.png";
import galeria4 from "../../../assets/img/producto/gallery/galeria4.png";
import galeria5 from "../../../assets/img/producto/gallery/galeria5.png";
const Gallery = ({ renderCarousel, imagenes }) => {
  const [imagenPrincipal, setImagenPrincipal] = useState("");
  const [imagenesExtras, setImagenesExtras] = useState([]);


  const getImagenPrincipal = (imagenes) => {
    const imagenesE = [];
    imagenes.map((imagen) => {
      if (imagen.fotoPrincipal) {
        setImagenPrincipal(imagen.url);
      } else {
        imagenesE.push(imagen.url);
      }

      setImagenesExtras(imagenesE);
    });
  };

  useEffect(() => {
    getImagenPrincipal(imagenes);
  }, []);

  return (
    <div className="container gallery">
      <img src={imagenPrincipal} className="one" />
      <img src={imagenesExtras[0]} className="two" />
      <img src={imagenesExtras[1]} className="three" />
      <img src={imagenesExtras[2]} className="four" />
      <img src={imagenesExtras[3]} className="five" />
      <a className="more" onClick={renderCarousel}>
        Ver más
      </a>
    </div>
  );
};

export default Gallery;
