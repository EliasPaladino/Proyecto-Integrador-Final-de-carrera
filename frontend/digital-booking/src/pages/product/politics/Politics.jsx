import React, { useEffect, useState } from "react";
import "./politics.css";

const Politics = ({ politicas }) => {

  const [politica, setPolitica] = useState([]);

  const getDescription = (politicas) => {
    const descripciones = [{
      titulo: "",
      descripciones: [],
    }, {
      titulo: "",
      descripciones: [],
    }, {
      titulo: "",
      descripciones: [],
    }];

    politicas.map((politica) => {
      switch (politica.tipoPolitica.id) {
        case 1:
          descripciones[0].titulo = politica.tipoPolitica.tipoPolitica;
          descripciones[0].descripciones.push(politica.politica);
          break;
        case 2:
          descripciones[1].titulo = politica.tipoPolitica.tipoPolitica;
          descripciones[1].descripciones.push(politica.politica);
          break;
        case 3:
          descripciones[2].titulo = politica.tipoPolitica.tipoPolitica;
          descripciones[2].descripciones.push(politica.politica);
          break;
      }
    });

    return descripciones;
  };

  useEffect(() => {
    setPolitica(getDescription(politicas));
  }, []);

  const obtenerPoliticas = (descriptions) => {

   return(
    descriptions.map((item, index) => {
      return (
        <div className="politica" key={index}>
          <h3 className="politicas-titulo" key={index}>
            {item.titulo}
          </h3>
          {item.descripciones.map((desc, index) => {
            return (
              <p className="politicas-texto" key={index}>
                {desc}
              </p>
            );
          })}
        </div>
      );
    })
   )
  };

  return (
    <div className="politicas-container">
      <div className="container">
        <h2>¿Qué tenés que saber?</h2>
        <hr />
        <div className="politicas">{obtenerPoliticas( politica )}</div>
      </div>
    </div>
  );
};

export default Politics;
