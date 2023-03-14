import React, { useEffect, useState, useContext } from "react";
import { Link } from "react-router-dom";
import "../../styles/common.css";
import "./signup.css";
import Header from "../../components/header/Header";
import Footer from "../../components/footer/Footer";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import urls from "../../enum/api-urls";


function SignUp() {
  const initialData = {
    name: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  };

  const [showPassword, setShowPassword] = useState(false);
  const [form, setForm] = useState(initialData);
  const [errors, setErrors] = useState({});
  const [errorGeneral, setErrorGeneral] = useState(false);
  const [saving, setSaving] = useState(false);

  const signupUser = () => {
    const urlSignup = urls.signup;
    const urlLogin = urls.login;
    setSaving(true);
    const data = {
      nombre: form.name,
      apellido: form.lastName,
      ciudad: form.city,
      email: form.email,
      contrasenia: form.password,
    };

    fetch(urlSignup, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }).then(function (response) {
      if (response.ok) {
        fetch(urlLogin, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(
             {
              email: data.email,
              contrasenia: data.contrasenia,
            }
          ),
        })
          .then((res) => res.json())
          .then((result) => {
            window.localStorage.setItem("email", form.email);
            window.localStorage.setItem("nombre", result.nombre);
            window.localStorage.setItem("apellido", result.apellido);
            window.localStorage.setItem("jwt", "Bearer " + result.jwt);
            window.localStorage.setItem("tiempoToken", new Date().getTime());
            window.localStorage.setItem("usuarioId", result.usuarioId);
            window.location.href = "/";
            setSaving(false);
          }, e=> {
            setSaving(false);
          });
      } else if (response.status === 409) {
        setErrors({ email: "El email ingresado ya fue registrado" });
        setSaving(false);
      } else {
        setErrorGeneral(true);
        setSaving(false);
      }
    });
  };

  const handleChange = (e) => {
    setForm({
      //le paso una copia del formulario anterior
      ...form,
      //actualizamos la propiedad que se esta modificando
      [e.target.name]: e.target.value,
    });
  };

  const validateForm = (formData) => {
    //declaramos la variable que vamos a retornar
    let errors = {};
    //validamos que el nombre no este vacio
    if (!formData.name.trim()) {
      errors.name = "El campo 'Nombre' es requerido";
    }
    //validamos que el apellido no este vacio
    if (!formData.lastName.trim()) {
      errors.lastName = "El campo 'Apellido' es requerido";
    }
    //validamos que la ciudad no este vacia
    if (!formData.city.trim()) {
      errors.city = "El campo 'Ciudad' es requerido";
    }
    //validamos que el email no este vacio
    if (!formData.email.trim()) {
      errors.email = "El campo 'Email' es requerido";
    }
    //validamos que la contraseña tenga mas de 6 caracteres
    if (formData.password.length <= 6) {
      errors.password = "La contraseña debe tener más de 6 caracteres";
    }
    //validamos que la contraseña y la confirmacion de la contraseña sean iguales
    if (formData.password !== formData.confirmPassword) {
      errors.confirmPassword = "Las contraseñas no coinciden";
    }
    return errors;
  };

  const handleBlur = (e) => {
    //cuando los elementos del formulario pierden el foco de la pagina, se desencadenan las validaciones
    //1. debe actualizarse el estado del form nuevamente de la misma manera que se realizo en el handleChange
    handleChange(e);
    //2. en la variable que configuramos para los errores, a cada elemento que tenga un error, tendriamos que ir asignandole un mensaje que despues mostraremos en el DOM
    setErrors(validateForm(form));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setErrors(validateForm(form));
    //evaluo si la longitud del objeto errors es cero y si el formulario tiene datos cargados para evitar que un usuario envio un formulario vacio sin haber cliqueado en un input
    if (
      Object.keys(errors).length === 0 &&
      form.name.length > 0 &&
      form.lastName.length > 0 &&
      form.city.length > 0 &&
      form.email.length > 0 &&
      form.password.length > 0 &&
      form.confirmPassword.length > 0
    ) {
      //se debe redireccionar a la pagina home y cambiar el header como si el usuario estuviera logueado
      ///simulamos la redireccion

      signupUser();
      //window.location.href = "/";
    }
  };

  return (
    //debe aparecer el header solo con el boton de iniciar sesion
    <>
      <Header />
      <form onSubmit={handleSubmit} className="formUsers">
        <h2>Crear cuenta</h2>

        {errorGeneral ? (
          <p className="errorSignup">
            Lamentablemente no ha podido registrarse. Por favor, intente más
            tarde.
          </p>
        ) : null}
        <div className="name-group">
          <div className="field">
            <label htmlFor="inputName">Nombre</label>
            <input
              type="text"
              name="name"
              id="inputName"
              onChange={handleChange}
              onBlur={handleBlur}
              value={form.name}
              className={`${errors.name ? "inputInvalid" : "inputValid"}`}
            />

            {
              //si el atributo 'name' de la variable errors tiene contenido, entonces mostramos el mensaje de error
              errors.name && <p className="error">{errors.name}</p>
            }
          </div>

          <div className="field">
            <label htmlFor="inputLastName">Apellido</label>
            <input
              type="text"
              name="lastName"
              id="inputLastName"
              onChange={handleChange}
              onBlur={handleBlur}
              value={form.lastName}
              className={`${errors.lastName ? "inputInvalid" : "inputValid"}`}
            />

            {errors.lastName && <p className="error">{errors.lastName}</p>}
          </div>
        </div>

        <div className="field">
          <label htmlFor="inputCity">Ciudad</label>
          <input
            type="text"
            name="city"
            id="inputCity"
            onChange={handleChange}
            onBlur={handleBlur}
            value={form.city}
            className={`${errors.city ? "inputInvalid" : "inputValid"}`}
          />

          {errors.city && <p className="error">{errors.city}</p>}
        </div>

        <div className="field">
          <label htmlFor="inputEmail">Correo electrónico</label>
          <input
            type="email"
            name="email"
            id="inputEmail"
            onChange={handleChange}
            onBlur={handleBlur}
            value={form.email}
            className={`${errors.email ? "inputInvalid" : "inputValid"}`}
          />

          {errors.email && <p className="error">{errors.email}</p>}
        </div>

        <div className="field">
          <label htmlFor="inputPassword">Contraseña</label>
          <div className="input-password">
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              id="inputPassword"
              onChange={handleChange}
              onBlur={handleBlur}
              value={form.password}
              className={`${errors.password ? "inputInvalid" : "inputValid"}`}
            />
            {
              <span
                onClick={() => {
                  setShowPassword(!showPassword);
                }}
                className="eye-icon"
              >
                {showPassword ? <FaEye /> : <FaEyeSlash />}
              </span>
            }
          </div>

          {errors.password && <p className="error">{errors.password}</p>}
        </div>

        <div className="field">
          <label htmlFor="inputConfirmPassword">Confirmar contraseña</label>
          <input
            type={showPassword ? "text" : "password"}
            name="confirmPassword"
            id="inputConfirmPassword"
            onChange={handleChange}
            onBlur={handleBlur}
            value={form.confirmPassword}
            className={`${
              errors.confirmPassword ? "inputInvalid" : "inputValid"
            }`}
          />

          {errors.confirmPassword && (
            <p className="error">{errors.confirmPassword} </p>
          )}
        </div>

        <div className={`loader ${saving ? "" : "hide"}`}></div>
        <button id="signupButton" type="submit"  className={` ${saving ? "hide" : ""}`}>
          Crear cuenta
        </button>
        <p>
          ¿Ya tienes una cuenta? <Link to="/login">Iniciar sesión</Link>
        </p>
      </form>
      <Footer />
    </>
  );
}

export default SignUp;
