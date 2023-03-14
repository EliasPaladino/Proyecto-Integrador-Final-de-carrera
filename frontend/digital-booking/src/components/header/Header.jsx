import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { ReactComponent as Logo } from "../../assets/img/logo.svg";
import botonMenu from "../../assets/img/menu.png";
import "./header.css";
import Offcanvas from "./offcanvas/Offcanvas";
import "./offcanvas/offcanvas.css";
import Navbar from "./navbar/Navbar";
import "./navbar/navbar.css";
import usuario from "../../assets/img/usuario.png";
import User from "./user/User";

export default function Header() {
  const [login, setLogin] = useState(false);
  const [menuActivo, setMenuActivo] = useState(false);
  const path = useLocation().pathname;

  function activarMenu() {
    setMenuActivo(!menuActivo);
  }

  useEffect(() => {
    const userJson = window.localStorage.getItem("nombre");

    if (userJson) {
      setLogin(true);
    }
  }, []);

  const logout = () => {
    window.localStorage.clear();
    setLogin(false);
    window.location.reload();
  };

  return (
    <header className="header">
      <div className="container barra">
        <div className="logo-container">
          <Link to="/home" className="logo">
            <Logo />
          </Link>
          <Link to="/home" className="lema">
            Sentite como en tu hogar
          </Link>
        </div>

        <a href="#" onClick={activarMenu}>
          <img className="botonMenu" src={botonMenu} />
        </a>

        <Offcanvas
          menuActivo={menuActivo}
          activarMenu={activarMenu}
          path={path}
          login={login}
          logout={logout}
        />
        {login ? (
          <User 
          activarMenu={menuActivo} 
            logout={logout}/>
        ) : (
          <Navbar className="navbar" path={path} />
        )}
      </div>
    </header>
  );
}
