import React from "react";
import Icon from "./Icon.svg"
import './horario.css'

const Horario = ({onSelect}) => {

        const horario = ["01:00 AM","02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM", "12:00 PM"]

  return (
    <div className="horario-container">
      <h2>Tu horario de llegada</h2>
      <div className="horario-info-container">
        <div className="horario-info">
            <img src={Icon} alt="icon" />
            <p>Tu habitación va a estar lista para el check-in entre las 10:00 AM y las 11:00 PM</p>
        </div>
        <label>Indicá tu horario estimado de llegada </label>
        <select onChange={onSelect} name="horario" >
        <option value="Seleccionar hora" selected disabled>Seleccionar hora de llegada</option>
        {
            horario.map( hora => {
            return (<option key={hora} value={hora}>{hora}</option>)      
        })
        }</select>
      </div>
    </div>
  );
};

export default Horario;
