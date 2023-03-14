/* Hardcodeo imagenes de prueba */
import galeria1 from "../../../assets/img/producto/gallery/galeria1.png";
import galeria2 from "../../../assets/img/producto/gallery/galeria2.png";
import galeria3 from "../../../assets/img/producto/gallery/galeria3.png";
import galeria4 from "../../../assets/img/producto/gallery/galeria4.png";
import galeria5 from "../../../assets/img/producto/gallery/galeria5.png";

import React from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from "react-responsive-carousel";
import "./carousel.css";

const CarouselMob = ({ imagenes }) => {
  return (
    <Carousel
      className="carouselMob"
      showThumbs={false}
      autoPlay={true}
      interval="3000"
      infiniteLoop={true}
      showArrows={false}
      showIndicators={false}
    >
      {imagenes.map((imagen) => (
        <div key={imagen.id} >
          <img src={imagen.url} width={"200vw"} height={"450vh"}/>
        </div>
      ))}
    </Carousel>
  );
};

export default CarouselMob;
