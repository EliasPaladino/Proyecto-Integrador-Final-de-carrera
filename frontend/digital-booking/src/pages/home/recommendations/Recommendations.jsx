import React, { createContext, useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./recommendations.css";
import urls from "../../../enum/api-urls";
import {
  FaMapMarkerAlt,
  FaHeart,
  FaStar,
  FaSwimmer,
  FaWifi,
  FaRegHeart,
  FaRegFrown,
} from "react-icons/fa";
import httpClient from "../../../security/interceptor";


export default function Recommendations({ products }) {
  const [fav, setFav] = useState(null);
  const [puntajes, setPuntajes] = useState(null);
  const [login, setLogin] = useState(false);
  const [isloaded, setLoaded] = useState(false)
  const id = localStorage.getItem("usuarioId");

  const handleFav = () => {
    setFav(!fav);
  };

  const getData = async () => {
    if(!puntajes){
      const url = urls.puntuaciones;
      const response = await fetch(url);
      const data = await response.json();
      setPuntajes(data);
    }
  };

  const getFavorite = async () => {
    if(!fav){
      const url = urls.favoritos;
      const response = await fetch(url + `/usuario/${id}`);
      const data = await response.json();
      setFav(data);
    }
  }

  useEffect(() => {
    getData();
    const userJson = window.localStorage.getItem("nombre");
    if (userJson) {
      setLogin(true);
      getFavorite();
    }
  }, []);

  const autocompleteData = null;
  const lista =
    products && products.length > 0 ? (
      products.map((item) => {
        const arrayPuntaje = puntajes && puntajes.length ? puntajes.filter(
          (punt) => punt.producto.id === item.id
        ) : [];
        const puntajeGeneral = arrayPuntaje.length ?arrayPuntaje.reduce(
          (a, b) => a + (b["puntuacion"] || 0),
          0
        ): 0;
        const puntajePromedio = isNaN(puntajeGeneral / arrayPuntaje.length)? 0: puntajeGeneral / arrayPuntaje.length;
        item.puntajePromedio = puntajePromedio
        return (
          <div key={item.id} className="recommendations">
            <div className="recommendations-images">
              <img
                className="recommendations-image"
                src={item.image} rel="preload"
                alt="Logo"
              />
              <div className="heart" onClick={handleFav}>
                
                { login ? <FaHeart /> /*() => { 
                  fav.map( fav => {
                    if (fav.producto.id === item.id) {
                      return <FaHeart />
                    }   
                    else {
                      return <FaRegHeart />
                    } 
                }) */

                 : <FaRegHeart />}
              </div>
            </div>

            <section className="recommendations-information">
              <div className="recommendations-qualifications">
                <div className="recommendations-sub-information">
                  <div className="recommendations-category-stars">
                    <span className="recommendations-category">
                      {item.categoria?.titulo?.toUpperCase()}
                    </span>
                  </div>

                  <span className="recommendations-title">{item.titulo}</span>
                </div>
                <div className="recommendations-qualifications-points">
                  <div className="recommendations-qualifications-number">
                    <span className="recommendations-qualifications-span">{parseInt(item.puntajePromedio)}</span>
                  </div>
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
                </div>
              </div>
              <section>
                <div className="recommendations-location">
                  <FaMapMarkerAlt></FaMapMarkerAlt>
                  <span className="recommendations-description">
                    A {item.distanciaCentro} km del centro
                  </span>
                  <span
                    className="recommendations-more"
                    onClick={() =>
                      window.open(
                        `https://www.google.com/maps/search/${item.latitud},${item.longitud}`,
                        "_blank"
                      )
                    }
                  >
                    {" "}
                    MOSTRAR EN EL MAPA
                  </span>
                </div>
                <div>
                  {item.caracteristicas && item.caracteristicas.length ?item.caracteristicas.map((d) => {
                    return (
                      <img
                        key={d.id}
                        className="recommendations-characteristics"
                        src={d.icono}
                      />
                    );
                  }): null}
                </div>
              </section>
              <span className="recommendations-description">
                {item.descripcion?.cuerpo.replace(/\\n/g, "\n")}
              </span>
              <Link
                className="recommendations-button"
                type="button"
                to={"/producto/" + item.id}
              >
                Ver más
              </Link>
            </section>
          </div>
        );
      })
    ) : (
      <>
        <div className="no-recommendations">
          <h1>
            {" "}
            Lo sentimos <FaRegFrown /> ... No existen alojamientos para la
            ciudad seleccionada.
          </h1>
        </div>
      </>
    );

  return (
    <section className="container-recommendations-component"  id="recommendations-section">
      <div className="container">
        <h3>
          Recomendaciones {autocompleteData ? "en: " + autocompleteData : null}
        </h3>
        <div className="recommendations-container">{lista} </div>
      </div>
    </section>
  );
}
