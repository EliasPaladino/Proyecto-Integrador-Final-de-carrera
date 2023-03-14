import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import location from "../../../assets/img/icons/location.svg";
import rank from "../../../assets/img/icons/rank.svg";
import urls from "../../../enum/api-urls";
import httpClient from "../../../security/interceptor";
import "./productLocation.css";

// Recibe la ubicacion por props
export default function ProductLocation( { ciudad, pais, distancia } ) {
  const [puntajePromedio, setPuntaje] = useState(0);
  const { id } = useParams();
  
  const getData = async () => {
    if(!puntajePromedio){
      const url = urls.puntuaciones;
      const response = await httpClient(url);
      const data = await response.data;
      const products =  data.filter(d => d.producto.id === parseInt(id) );
      if(products && products.length){
        const puntuacion = products.reduce(
          (a, b) => a + (b["puntuacion"] || 0),
          0
        )
        setPuntaje(puntuacion / products.length);
      }
    }
  };
  
  useEffect(() => {
    getData();
  }, []);

  return (
    <div className="product-location-container">
      <div className="container product-location">
        <div className="product-location__info">
          <img src={location} alt="location" />
          <div className="product-location__info-text">
            <p>{ciudad}, {pais}</p>
            <p>A {distancia} km del centro</p>
          </div>
        </div>
        <div className="product-product-location__info-text">
          {puntajePromedio == 0 ? (
            <span className="recommendations-qualifications-title">
              Sin puntaje
            </span>
          ) : puntajePromedio > 0 && puntajePromedio < 4 ? (
            <span className="recommendations-qualifications-title">
              Malo
            </span>
          ) : puntajePromedio >= 4 && puntajePromedio < 6 ? (
            <span className="recommendations-qualifications-title">
              Regular
            </span>
          ) : puntajePromedio >= 6 && puntajePromedio < 8 ? (
            <span className="recommendations-qualifications-title">
              Bueno
            </span>
          ) : puntajePromedio >= 8 && puntajePromedio < 9 ? (
            <span className="recommendations-qualifications-title">
              Muy bueno
            </span>
          ) : (
            <span className="recommendations-qualifications-title">
              Excelente
            </span>
          )}
          
          <div className="recommendations-qualifications-number">
            <span className="recommendations-qualifications-span">{parseInt(puntajePromedio)}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
