import React from "react";
import Footer from "../../../components/footer/Footer";
import Header from "../../../components/header/Header";
import './success.css';
import IconSuccess from './IconSuccess.svg';
import { Link, useParams } from "react-router-dom";

const Success = () => {
  
  const { id } = useParams();
  return (
    <>
      <Header />
      <div className="success-container">
        <div className="success-container-info">
            <img src={IconSuccess} alt="IconSuccess" />
            <h2>¡Muchas gracias!</h2>
            <p>{id?'Su reserva se ha realizado con éxito':'Tu propiedad se ha creado con éxito.'}</p>
            <button className="btn-1"><Link to="/home">ok</Link></button>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Success;
