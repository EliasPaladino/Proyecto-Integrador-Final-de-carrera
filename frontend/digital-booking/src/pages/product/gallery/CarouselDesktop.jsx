/* Hardcodeo imagenes de prueba */
import galeria1 from "../../../assets/img/producto/gallery/galeria1.png";
import galeria2 from "../../../assets/img/producto/gallery/galeria2.png";
import galeria3 from "../../../assets/img/producto/gallery/galeria3.png";
import galeria4 from "../../../assets/img/producto/gallery/galeria4.png";
import galeria5 from "../../../assets/img/producto/gallery/galeria5.png";

import React from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from "react-responsive-carousel";
import Header from "../../../components/header/Header";
import Footer from "../../../components/footer/Footer";
import "./carousel.css";

const CarouselDesktop = ({ carouselActivo, renderCarousel, imagenes }) => {

  return (
    <div className={carouselActivo ? "carousel-template" : ""}>
      <Header />
      <div className="carousel-desktop">
        <Carousel
          showThumbs={false}
          autoPlay={true}
          interval="3000"
          infiniteLoop={true}
          showArrows={false}
          showIndicators={false}
        >
          {imagenes.map((imagen) => (
            <div key={imagen.id} >
              <img src={imagen.url} width={"200vw"} height={"450vh"} />
            </div>
          ))}</Carousel>
        <a className="close" onClick={renderCarousel}>
          X
        </a>
      </div>

      <Footer />
    </div>
  );
};

export default CarouselDesktop;
