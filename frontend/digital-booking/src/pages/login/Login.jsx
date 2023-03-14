import {Link, useParams} from "react-router-dom"
import React, { useState, useEffect } from "react"
import ReactDOM from 'react-dom'
import "../../styles/common.css";
import "./login.css";
import Header from '../../components/header/Header'
import Footer from '../../components/footer/Footer'
import urls from "../../enum/api-urls";
import { FaExclamationCircle } from "react-icons/fa";

//pagina del formulario de login
function Login(){
    const { showError } = useParams();

    const initialData = {
        email: "",
        contrasenia: ""
    }

    const [form, setForm] = useState(initialData);
    const [error, setError] = useState(true);
    const [errorMessage, setErrorMessage] = useState(false);
    const [errorGeneral, setErrorGeneral] = useState(false);


    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    }

    const login = async () => {
        const url = urls.login;  //'http://localhost:8080/login';  
        const response = await fetch(url, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(form),
          })
          .then((res) => res.json())
          .then((response) => {
              
            if (response) {
                window.localStorage.setItem('email', form.email);
                window.localStorage.setItem('nombre', response.nombre);
                window.localStorage.setItem('apellido', response.apellido);
                window.localStorage.setItem('ciudad', response.ciudad);
                window.localStorage.setItem('jwt', 'Bearer '+ response.jwt);
                window.localStorage.setItem('tiempoToken', new Date().getTime());
                window.localStorage.setItem('usuarioId',response.usuarioId);
                window.localStorage.setItem('rol', response.rol);   
                window.location.href = "/";
                if(showError){
                    window.location.href = "/producto/"+ showError;
                }else {
                    window.location.href = "/";
                }
            } else if(response.status === 401){
                setErrorMessage(true);
            } else setErrorGeneral(true);
          }, error => {
            setErrorMessage(true);
        });
    };
    
    const handleSubmit = (e) => {
        e.preventDefault();
        login()
    }

    window.scrollTo({top: 0, left: 0, behavior: 'smooth'});

    return(
        //debe aparecer el header con el boton de crear cuenta
        //retorna el formulario
        <>
            <Header/>
            <form onSubmit={handleSubmit} className="formUsers">
                <div className={`login-error-container ${!showError? "hide" : ""}`}>
                    <span className="login-error-message">
                        <FaExclamationCircle></FaExclamationCircle>Para realizar una reserva necesitas estar logueado 
                    </span>
                </div>
                <h2>Iniciar sesión</h2>

                {
                    errorMessage ? <p className="errorLogin">Por favor vuelva a intentarlo, sus credenciales son inválidas.</p> : null
                }
                {
                    errorGeneral ? <p className="errorLogin">Lamentablemente no ha podido iniciar sesión. Por favor, intente más tarde.</p> : null
                }

                <div className="field">
                    <label htmlFor="inputEmail">Correo electrónico</label>
                    <input type="email" name='email' id='inputEmail' onChange={handleChange} value={form.email} required/>

                </div>


                <div className="field">
                    <label htmlFor="inputPassword">Contraseña</label>
                    <input type="password" name='contrasenia' id='inputPassword' onChange={handleChange} value={form.contrasenia} required/>

                </div>

                <button id="loginButton" type="submit">Ingresar</button>
                <p>¿Aún no tienes cuenta? <Link to="/signup">Registrate</Link></p>
            </form>
            <Footer/>
        </>
    )
};

export default Login;