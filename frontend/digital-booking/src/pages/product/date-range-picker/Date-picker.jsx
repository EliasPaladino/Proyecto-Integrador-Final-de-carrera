import { addDays, differenceInCalendarDays, differenceInDays } from "date-fns";
import React, { useEffect, useState } from "react";
import { DateRangePicker } from "react-date-range";
import { Link, useParams } from "react-router-dom";
import "./Date-picker.css";
import * as locales from 'react-date-range/dist/locale';
import urls from "../../../enum/api-urls";
import httpClient from "../../../security/interceptor";

export default function ProductDatePicker( { title, showInit, sendDate }) {
  
  const { id } = useParams();

  const [locale, setLocale] = React.useState('es');
  const [userData, setUserData] = useState(null);
  const [state, setState] = useState([
    {
      startDate: new Date(),
      endDate: addDays(new Date(), 1),
      key: "selection",
      color: "#1DBEB4",
    },
  ]);

  
  const sendDates = (data) => {
    setState(data);
    window.sessionStorage.setItem('startDate', data[0].startDate)
    window.sessionStorage.setItem('endDate', data[0].endDate)
    if(sendDate){
      sendDate(data);
    }
  }

  const [disabledDates, setDisabledDates] = React.useState([]);
  
  const obtenerReservas = async (data) => {
    const url = urls.reservasXProducto + id ;
  
    if(data && data.jwt){
      const response = await httpClient(url, {
        method: "GET",
        headers: {
          "Authorization": data?.jwt, 
          "Access-Control-Allow-Headers" : "Content-Type",
          'Access-Control-Allow-Origin': '*',
          "Access-Control-Allow-Methods": "OPTIONS,POST,GET"
        },
      })
      
        .then((res) => res.data)
        .then((response) => {
          let dates = [];
          response.forEach(element => {
            dates = dates.concat(getDatesInRange(element.fechaInicio, element.fechaFin))
          });
          setDisabledDates(dates);
        }, error => {
      });
    }
  };

  const  getDatesInRange = (startDate, endDate) => {
    startDate = new Date(startDate)
    endDate = new Date(endDate)
    const dates = [];
    let currentDate = new Date(
        startDate.getFullYear(),
        startDate.getMonth(),
        startDate.getDate()
    );
    while (currentDate <= endDate) {
        dates.push(currentDate);

        currentDate = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth(),
            currentDate.getDate() + 1, 
        );
    }
    return dates;
  }
  
  const obtenerDatosUsuario = () => {
    const data = {nombre: window.localStorage.getItem("nombre"),
    jwt: window.localStorage.getItem("jwt"),
    apellido: window.localStorage.getItem("apellido"),
    tiempoToken: window.localStorage.getItem("tiempoToken")};
    setUserData(data);
    obtenerReservas(data);
  };

  useEffect(() => {
    const startDate = window.sessionStorage.getItem('startDate')
    const endDate = window.sessionStorage.getItem('endDate')
    if(startDate && endDate){
      setState([{
        startDate: new Date(startDate), 
        endDate: new Date(endDate)
      }])
    }
    obtenerDatosUsuario();
  }, []);

  const orientation = window.matchMedia("(max-width: 700px)").matches ? 1 : 2;

  return (
    <div className={`product-date-picker ${showInit ? "" : "showInit"}`}>
      <div className={`container-date-picker ${showInit ? "container" : ""}`}>
        <h2>{ title } </h2>
        <section className="product-date-container">
          <div className="product-date-container-date-picker"> 
            <DateRangePicker
              locale={locales[locale]}
              minDate={new Date() }
              onChange={(item) => sendDates([item.selection])}
              showSelectionPreview={true}
              moveRangeOnFirstSelection={false}
              months={orientation}
              ranges={state}
              disabledDates={disabledDates}
              direction="horizontal"
            />
            <span className={`${userData?.nombre ? "hide" : ""}`}>Inicia sesión para ver las fechas disponibles</span>
          </div>
          <div className={`product-add ${showInit ? "" : "hide"}`}>
            <h4>Agrega tus fechas de viaje para obtener precios exactos</h4>
            <div type="button" className="product-date-button">
              <Link to={window.location.pathname + "/reserva"}  className={`${userData?.nombre ? "" : "hide"}`}>Iniciar Reserva</Link>
              <Link to={"/login/"+id}  className={`${userData?.nombre ? "hide" : ""}`}>Iniciar Reserva</Link>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}
