import React, { useEffect, useState } from "react";
import "./cardDetail.css";
import iconStar from "./iconStar.svg";
import location from "../../../assets/img/icons/location.svg";
import { format } from "date-fns";

const CardDetail = ({ imagenes, categoria, titulo, ciudad, pais, fechas, onSave, direccion }) => {
  // const puntaje = 5;
  // const stars = [];
  // for (let i = 0; i < puntaje; i++) {
  //   stars.push(<img className="icon-star" src={iconStar} alt="iconStar" />);
  // }

  const [imagenPrincipal, setImagenPrincipal] = useState('');

  const getImagenPrincipal = ( imagenes ) => {
    imagenes.map( imagen => {
      if(imagen.fotoPrincipal) 
        setImagenPrincipal(imagen.url)
    })
  }

  useEffect(() => {
    getImagenPrincipal(imagenes);
  }, []);

  return (
    <div className="card-detail-container">
      <h2 className="card-detail-title">Detalle de la reserva </h2>

      <div className="card-detail">
        <img className="card-detail-img" src={imagenPrincipal} alt="imagen" />

        <div className="card-detail-body">
          <div className="card-detail-info">
            <p>{categoria?.toUpperCase()}</p>
            <h2>{titulo}</h2>
          </div>

          <div className="card-detail-location">
            <img src={location} alt="location" />
            <p>
              {direccion}, {ciudad}, {pais}
            </p>
          </div>
          <div className="check">
            <hr />
            <div className="check-info">
              <p>Check-in</p>
              <span>{fechas?  format(fechas.startDate, 'dd-MM-yyyy'):'_/_/_'}</span>
            </div>
            <hr />
            <div className="check-info">
              <p>Check-out</p>
              <span>{fechas?  format(fechas.endDate, 'dd-MM-yyyy'):'_/_/_'}</span>
            </div>
            <hr />
          </div>
          <button className="reserva-btn" onClick={onSave}>Confirmar reserva</button>
        </div>
      </div>
    </div>
  );
};

export default CardDetail;
