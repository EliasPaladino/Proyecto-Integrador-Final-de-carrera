import React, { useState, useEffect } from "react";
import Header from "../../components/header/Header";
import ProductHeader from "../product/productHeader/ProductHeader";
import "./admin.css";
import Atributo from "./atributos/Atributo";
import Formulario from "./formulario/Formulario";
import Input from "./input/Input";
import Politicas from "./politicas/Politicas";
import urls from "../../enum/api-urls";
import swal from "sweetalert";
import Footer from "../../components/footer/Footer";
import httpClient from "../../security/interceptor";

const Admin = () => {
  const [guardar, setGuardar] = useState({});
  const [imagenes, setImagenes] = useState();
  const [user, setUser] = useState();

  const getUserData = () => {
    const data = {
      nombre: window.localStorage.getItem("nombre"),
      jwt: window.localStorage.getItem("jwt"),
      apellido: window.localStorage.getItem("apellido"),
      ciudad: window.localStorage.getItem("ciudad"),
      tiempoToken: window.localStorage.getItem("tiempoToken"),
      email: window.localStorage.getItem("email"),
      usuarioId: window.localStorage.getItem("usuarioId"),
    };
    setUser(data);
  };

  const guardarProducto = async (data) => {
    const url = urls.crearProducto;
    if (user) {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          Authorization: user?.jwt,
          "Content-Type": "application/json; charset=utf8",
        },
        body: JSON.stringify(data),
      })
        .then((res) => res.data)
        .then(
          (response) => {
            window.location.href = "/admin/success";
          },
          (error) => {
            console.log(error)
            swal(error);
          }
        );
    }
  };

  console.log(guardar);

  const saveTitle = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, titulo: value});
  }

  const saveAddress = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, direccion: value});
  }

  const saveDescription = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, descripcion: {cuerpo: value, titulo: "Alojate en el lugar ideal"}});
  }

  function saveCity(cityId) {
    setGuardar({...guardar, ubicacion: {id: cityId}})
  }

  function saveCategory(categoryId) {
    setGuardar({...guardar, categoria: {id: categoryId}}) 
  }

  const saveLatitud = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, latitud: value});
  }

  const saveLongitud = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, longitud: value});
  }

  const saveDistancia = (event) => {
    const value = event.target.value;   
    setGuardar({...guardar, distanciaCentro: value});
  }

  const savePoliticas = (arrayPoliticas) => {
    setGuardar({...guardar, politicas: arrayPoliticas})
  }

  const saveImagenes = ( arrayImagenes) => {
    setGuardar({...guardar, imagenes: arrayImagenes})
  }

  const setAtributos = (atributos) => { 
    atributos.inputs = atributos.inputs.concat([atributos.data]);
   
    setGuardar({...guardar, caracteristicas: atributos.inputs});
  }

 

  const save = () => {
    if(!guardar.titulo){
        swal('Ingresa un titulo para el producto');
      } else if(!guardar.categoria) {
        swal('Seleccioná una categoria para el producto');
      }  else if(!guardar.direccion) {
        swal('Ingresa una dirección para el producto');
      }else if(!guardar.ubicacion) {
        swal('Seleccioná una ciudad para el producto');
      } else if(guardar.caracteristicas && guardar.caracteristicas.length < 1) {
        swal('Ingresa al menos una caracteristica');
      } else if(guardar.politicas && guardar.politicas.length < 1) {
        swal('Ingresa al menos una politica');
      } else if(guardar.imagenes && guardar.imagenes.length < 1) {
        console.log(guardar)
        swal('Ingresa al menos una imagen');
      } else {
        guardar.imagenes[0].fotoPrincipal = true;
        guardar.caracteristicas = guardar.caracteristicas.map(c => {
          return{
            icono: c.icon,
            nombre: c.nombre
          }
        })
        guardar.categoria.id = guardar.categoria.id.toString();
        guardar.ubicacion.id = guardar.ubicacion.id.toString();
        guardarProducto({...guardar })
      }
  }

  useEffect(() => {
    getUserData()
  }, [])
  return (
    <div>
      <Header />
      <ProductHeader titulo={"Administración"} categoria={"nada"} />
      <Formulario saveTitle={saveTitle} saveAddress={saveAddress} saveDescription={saveDescription} saveDistancia={saveDistancia} saveLatitud={saveLatitud} saveLongitud={saveLongitud} saveCity={saveCity} saveCategory={saveCategory} />
      <div className="container admin-container">
        <h2>Agregar atributos</h2>
        <Atributo setAtributos={setAtributos}/>
        <h2>Políticas del producto</h2>
        <Politicas savePoliticas={savePoliticas} />
        <h2>Crear imágenes</h2>
        <div className="input-imagen">
          <Input placeHolder={"Insertar https://"} id='imagenes' saveImagenes={saveImagenes}/>
        </div>
        <button className="boton-admin" onClick={save}>Crear</button>
      </div>
    <Footer />
    </div>
  );
};

export default Admin;
