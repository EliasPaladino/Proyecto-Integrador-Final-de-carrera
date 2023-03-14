import React, { useState, useEffect } from "react";
import Autocomplete from "../../home/searcher/autocomplete/Autocomplete";
import urls from "../../../enum/api-urls";
import "./formulario.css";
import httpClient from "../../../security/interceptor";

const Formulario = ({ saveTitle, saveAddress, saveDescription, saveLatitud, saveLongitud, saveDistancia, saveCity, saveCategory }) => {
  const [ciudades, setCiudades] = useState();
  const [categorias, setCategorias] = useState();
  const [cityId, setCityId] = useState();
  const [categoryId, setCategoryId] = useState();
  const urlCiudades = urls.ciudades;
  const urlCategorias = urls.categorias;

  function compare(a, b) {
    if (a.nombre < b.nombre) {
      return -1;
    }
    if (a.nombre > b.nombre) {
      return 1;
    }
    return 0;
  }

  const getCiudades = async () => {
    const response = await httpClient(urlCiudades);
    const json = await response.data;
    setCiudades(json.sort(compare));
  };

  const getCategorias = async () => {
    const response = await httpClient(urlCategorias);
    const json = await response.data;
    setCategorias(json.sort(compare));
  };

  const getSelectId = (id) => {
    setCityId(id);
  }

  const handleCity = async (id) => {
    setCityId(id);
  };

  const handleCategory = async (id) => {
    setCategoryId(id);
  };

  useEffect(() => {
    getCiudades();
    getCategorias();
  }, []);

  return (
    <div className="container">
      <h2>Crear propiedad</h2>
      <form className="form-propiedades">
        <div className="form-grupo">
          <label>Nombre de la propiedad</label>
          <input
            type="text"
            placeholder="Casa en Mendoza con vistas"
            id="titulo"
            onChange={saveTitle}
          />
        </div>

        <div className="form-grupo">
        <label for="categorias">Categoria</label>
          <Autocomplete
            suggestions={
              !categorias
                ? null
                : categorias.map((item) => {
                    return {
                      title: item.titulo,
                      id: item.id,
                    };
                  })
            }
            handleChanges={handleCategory}
            placeholder="Categoria"
            selectId={saveCategory}
          />
        </div>

        <div className="form-grupo">
          <label>Dirección</label>
          <input
            type="text"
            placeholder="Cerro Las Damas 420"
            id="direccion"
            onChange={saveAddress}
          />
        </div>
        <div className="form-grupo">
          <label>Ciudad</label>
          <Autocomplete
            suggestions={
              !ciudades
                ? null
                : ciudades.map((item) => {
                    return {
                      title: item.nombre,
                      id: item.id,
                    };
                  })
            }
            handleChanges={handleCity}
            placeholder="Ciudad"
            selectId={saveCity}
          />
        </div>

        <div className="bloque-distancia">
          <div className="bloque-distancia__item">
            <label>Latitud</label>
            <input
              type="text"
              placeholder="-32.23456693125475"
              id="titulo"
              onChange={saveLatitud}
            />
          </div>
          <div className="bloque-distancia__item">
            <label>Longitud</label>
            <input
              type="text"
              placeholder="43.58372819923467"
              id="titulo"
              onChange={saveLongitud}
            />
          </div>
          <div className="bloque-distancia__item">
            <label>Distancia del centro</label>
            <input
              type="text"
              placeholder="43km"
              id="titulo"
              onChange={saveDistancia}
            />
          </div>
        </div>

        <div className="bloque-descripcion">
          <label for="descripcion">Descripción</label>
          <textarea
            name="descripcion"
            id="descripcion"
            cols="30"
            rows="10"
            placeholder="Escriba aquí"
            onChange={saveDescription}
          ></textarea>
        </div>
      </form>
    </div>
  );
};

export default Formulario;
