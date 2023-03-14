import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Header from "../../components/header/Header";
import Footer from "../../components/footer/Footer";
import ProductHeader from "../product/productHeader/ProductHeader";
import Politics from "../product/politics/Politics";

import "./booking.css";
import ProductDatePicker from "../product/date-range-picker/Date-picker";
import Horario from "./bloqueHorario/Horario";
import CardDetail from "./cardDetail/CardDetail";
import BookingForm from "./formulario/BookingForm";
import urls from "../../enum/api-urls";
import { format } from "date-fns"; 
import swal from 'sweetalert';
import httpClient from "../../security/interceptor";

const Booking = () => {
  const { id } = useParams();
  const [datos, setDatos] = useState({});
  const [user, setUserData] = useState();
  const [guardar, setGuardar] = useState({producto: {id: id}, 
    estadoVacunacion: true, 
    comentario: "", });

  const getData = async () => {
    getUserData();
    const url = urls.productos;
    const response = await httpClient(`${url}buscar/${id}`);
    const data = await response.data;
    setDatos(data);
  };

  const getUserData =  () => {
    const data = {
      nombre: window.localStorage.getItem("nombre"),
      jwt: window.localStorage.getItem("jwt"),
      apellido: window.localStorage.getItem("apellido"),
      ciudad: window.localStorage.getItem("ciudad"),
      tiempoToken: window.localStorage.getItem("tiempoToken"),
      email: window.localStorage.getItem("email"),
      usuarioId: window.localStorage.getItem("usuarioId")
    };
    setUserData(data)
  }

  const onSave =  () => {
    if(user.email){
      if(!guardar.horaIngreso){
        swal('Seleccioná un horario de ingreso');
      } else if(!guardar.fechaInicio) {
        swal('Seleccioná tu fecha de reserva');
      }  else if(guardar.fechaInicio === guardar.fechaFin) {
        swal(`Tu fecha de Check-out debe ser superior a la de Check-in (${guardar.fechaInicio})`);
      }else {
        guardarReserva({...guardar,  usuario: {nombre: user.nombre, apellido: user.apellido, email: user.email, id: user.usuarioId ? user.usuarioId: 5}})
      }
    } else {
      swal('Necesitas loguear primero.')
    }
    
  }

  const onSelect =  (event) => {
    const value = event.target.value.split(' ');
    const selectedHora = value[0].split(':')
    let hora = parseInt(selectedHora[0]);
    if(value[1] === 'PM'){ 
      hora += 12;
    } 
    setGuardar({...guardar, horaIngreso: hora + ":00:00"})
  }

  const guardarComentario = (event) => {
    const value = event.target.value;
    setGuardar({...guardar, comentario: value})
  }
   
  const guardarReserva = async (data) => {
    const url = urls.reservas ;
    if(user){
      const response = await httpClient(url, {
        method: "POST",
        headers: {
          "Authorization": user?.jwt, 
          "Content-Type": "application/json; charset=utf8"
        },
        data: JSON.stringify(data),
      })
      .then((res) => res.data)
      .then((response) => {
        if(response.id){
          window.location.href = "/producto/"+ id +"/reserva/success";
        } else {
          swal('Error al guardar la reserva')
        }
      }, error => {
        swal('Error al guardar la reserva')
      });
    }
  };

  console.log(guardar);

  const [fechas, setFechas] = useState();

  const sendDate = (data) => {
    setFechas(data[0])
    setGuardar({...guardar, fechaInicio: data[0] && data[0].startDate? format(data[0]?.startDate, 'yyyy-MM-dd'):null, 
    fechaFin: data[0] && data[0].endDate? format(data[0]?.endDate, 'yyyy-MM-dd'):null })
  }

  useEffect(() => {
    getData();
    
    const startDate = window.sessionStorage.getItem('startDate')? new Date(window.sessionStorage.getItem('startDate')):null;
    const endDate = new Date(window.sessionStorage.getItem('endDate'))
    if(startDate && endDate) { 
      setGuardar({...guardar, fechaInicio: startDate? format(startDate, 'yyyy-MM-dd'):null, 
      fechaFin: endDate? format(endDate, 'yyyy-MM-dd'):null })
      setFechas({startDate:startDate, endDate:endDate})
    }
  }, []);

  return (
    <>
      <Header />
      {datos.categoria && (
        <ProductHeader
          categoria={datos.categoria.titulo}
          titulo={datos.titulo}
        />
      )}
      <div className="booking-container">
        <div className="container booking-container-info">
          <div className="one">
            <BookingForm data={user} guardarComentario={guardarComentario} />
          </div>
          <div className="two">
            <ProductDatePicker title="Seleccioná tu fecha de reserva" sendDate={sendDate}/>
          </div>
          <div className="three">
            <Horario  onSelect={onSelect}/>
          </div>
          <div className="four">
            {datos.imagenes && (
              <CardDetail
                imagenes={datos.imagenes}
                categoria={datos.categoria.titulo}
                titulo={datos.titulo}
                ciudad={datos.ubicacion.nombre}
                pais={datos.ubicacion.pais}
                fechas={fechas}
                onSave={onSave}
                direccion={datos.direccion}
              />
            )}
          </div>
        </div>
      </div>

      {datos.politicas && <Politics politicas={datos.politicas} />}
      <Footer />
    </>
  );
};

export default Booking;
