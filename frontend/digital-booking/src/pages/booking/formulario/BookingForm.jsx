import React from "react";
import { Link } from "react-router-dom";
import "./bookingForm.css";

const BookingForm = ({ data, guardarComentario }) => {
  const userData = data
    ? data
    : { nombre: "", apellido: "", email: null, ciudad: "" };
  return (
    <div>
      <h2>Completá tus datos</h2>
      <div className="booking-form-container">
        <form>
          <div className="form-group">
            <label htmlFor="nombre">Nombre</label>
            <input
              type="text"
              className="form-control"
              id="nombre"
              placeholder="Nombre"
              value={userData ? userData.nombre : null}
              disabled={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="apellido">Apellido</label>
            <input
              type="text"
              className="form-control"
              id="apellido"
              value={userData ? userData.apellido : null}
              placeholder="Apellido"
              disabled={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">Correo electrónico</label>
            <input
              type="email"
              className="form-control"
              id="email"
              value={userData ? userData.email : null}
              placeholder="Correo"
              disabled={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="ciudad">Ciudad</label>
            <input
              type="text"
              className="form-control"
              id="ciudad"
              placeholder="ciudad"
              value={userData ? userData.ciudad : null}
              disabled={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="comentarios">Comentarios</label>
            <textarea
              name="comentarios"
              id="comentarios"
              cols="65"
              rows="4"
              placeholder="Escriba aquí"
              onChange={guardarComentario}
            ></textarea>
          </div>

          <label className="control control-checkbox">
            ¿Está vacunado contra el COVID-19?
            <input type="checkbox" />
            <div className="control_indicator"></div>
          </label>
        </form>
      </div>
    </div>
  );
};

export default BookingForm;
