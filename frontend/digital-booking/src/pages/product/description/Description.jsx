import React from "react";
import "./description.css";

const Description = ({ cuerpo, titulo }) => {

  return (
    <div className="description-container">
      <div className="container description">
        <h2>{titulo}</h2>
        <div className="description-text">
          <p>{cuerpo}</p>
        </div>
      </div>
    </div>
  );
};

export default Description;
