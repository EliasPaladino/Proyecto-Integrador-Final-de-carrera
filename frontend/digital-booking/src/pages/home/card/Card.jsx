import React, { Fragment, useEffect, useState } from "react";

import "./card.css";
import fondo from "../../../assets/img/imagen.png";

import urls from "../../../enum/api-urls";
import httpClient from "../../../security/interceptor";

export default function Card({categorySelected}) {
  
  const [categorias, setCategorias] = useState([{id:0.1,urlImagen: fondo},{id:0/2,urlImagen: fondo},{id:0.3,urlImagen: fondo},{id:0.4,urlImagen: fondo}]);


  const obtenerNumeroProductos = async (id) => {
    const response = await httpClient(urls.categoriasXProdctos + id)
    const json = await response.data;
    return json.length;
  };

  const obtenerCategorias = async () => {
    const response = await httpClient(urls.categorias)
    const json = await response.data;
    const promises =  json.map(function(c) {
       return obtenerNumeroProductos(c.id).then(
        result => {
          c.numeroProductos = result;
          return c;
        }
      )
    })
    Promise.all(promises).then(function(results) {
      setCategorias(results);
    })
  };

  useEffect(() => {
    obtenerCategorias();
  }, []);

  const [categoria, setCategoria] = useState();

  const onClick = (event, id) => {
    event.stopPropagation();
    if(categoria === id){
      setCategoria(null)
      categorySelected(null)
    } else {
      setCategoria(id)
      categorySelected(id)
    }
  }


  const lista = categorias ?categorias.map((item) => {
    return (
      <Fragment key={item.id}>
        <button onClick={ (event) => onClick(event, item.id) }  className={`card ${categoria ===item.id ? "active" : ""}`}>
          <div className="card-image">
              <img className="image" src={item.urlImagen} alt="Logo" />
          </div>
          <div className="card-content">
              <h3 className={`title ${item.titulo ? "" : "no-word"}`}>{item.titulo}</h3>
              <p className={`subtitle ${item.titulo ? "" : "no-word"}`}>{item.numeroProductos} {item.titulo}</p>
          </div>
        </button>
      </Fragment>
    );
  }): 
  (<section className="center">
    <div className="loader"></div>
  </section>);

  return (
    <section className="container-card-component">
      <div className="container">
        <h3>Buscar por tipo de alojamiento</h3>
        <div className="cards-container">{lista}</div>
      </div>
    </section>
  );
}
