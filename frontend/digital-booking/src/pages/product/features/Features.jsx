import React, { useContext } from "react";
import './features.css';

const Features = ( { caracteristicas }) => {
  const getFeatures = ( features ) => {
    return features.map( ( feature, index ) => {
        return ( 
            <div className="feature" key={index}>
                <img className="icon-feature" src={feature.icono} alt={feature.nombre} />
                <p>{feature.nombre}</p>    
            </div>
        )
    })
  };

  return (
    <div className="features-container">
      <div className="container">
        <h2>¿Qué ofrece este lugar?</h2>
        <hr></hr>
        <div className="features">
            {getFeatures(caracteristicas)}
        </div>
      </div>
    </div>
  );
};

export default Features;
