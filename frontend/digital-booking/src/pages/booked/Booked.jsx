import React, { Component, Fragment } from "react";
import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";
import urls from "../../enum/api-urls";
import httpClient from "../../security/interceptor";
import BookedItem from "./item/BookedItem";

class Booked extends React.Component {

    constructor(props) {
      super(props);
      this.state = { reservas: []}
    }
  
    async componentDidMount() {
        const url = urls.reservasXUsuario;
        const id = window.localStorage.getItem("usuarioId");
        const jwt = window.localStorage.getItem("jwt");
        const response = await httpClient(url + `${id}`,  {
            method: "GET",
            headers: {
              "Authorization": jwt, 
              "Content-Type": "application/json; charset=utf8"
            },
          });

        const responses = await response;
        if(responses.status === 200){
          const reservas = await response.data;
          this.setState({reservas: reservas})
        } else {
          window.location.href = "/";
        }
    }
    render() {
      if (this.state.reservas.length) {
        return (
          <>
              <Header />
              <BookedItem reservas={this.state.reservas} />
              <Footer />
          </>
        );
      } else {
        return (
          <>
              <Header />
              <BookedItem reservas={this.state.reservas} />
              <Footer />
          </>
        );
      }
    }
  }

  

  
export default Booked;