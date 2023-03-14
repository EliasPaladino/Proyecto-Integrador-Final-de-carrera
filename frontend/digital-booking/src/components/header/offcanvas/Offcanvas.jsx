import React, { useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import Redes from "../../footer/redes/Redes";
import usuario from "../../../assets/img/usuarioMobile.png";
import User from "../user/User";
import { FaThList } from "react-icons/fa";

export default function Offcanvas({
  menuActivo,
  activarMenu,
  path,
  login,
  logout,
}) {
  const id = useParams().id;
  const rol = localStorage.getItem('rol');
  const renderMenu = (url) => {
    switch (url) {
      case "/home":
        return (
          <>
            <li>
              <Link to="/signup">Crear cuenta</Link>
            </li>
            <li>
              <Link to="/login">Iniciar sesión</Link>
            </li>
          </>
        );
      case `/producto/${id}`:
        return (
          <>
            <li>
              <Link to="/signup">Crear cuenta</Link>
            </li>
            <li>
              <Link to="/login">Iniciar sesión</Link>
            </li>
          </>
        );
      case "/signup":
        return (
          <>
            <li>
              <Link to="/login">Iniciar sesión</Link>
            </li>
          </>
        );
      case "/login":
        return (
          <>
            <li>
              <Link to="/signup">Crear cuenta</Link>
            </li>
          </>
        );
    }
  };

  return (
    <nav className={menuActivo ? "" : "disabled"}>
      <div className="info">
        <a href="#" onClick={activarMenu}>
          X
        </a>
        {login ? (
          <>
          <User />
          </>
        ) : (
          <h3>MENÚ</h3>
        )}
      </div>
      <div className="enlaces">
        <ul>{login && rol == "ROLE_ADMIN"? 
          <li>
            <Link to="/admin">Administrador</Link>
          </li> : null}
        </ul>
        <ul>{login ? 
          <li>
            <Link to="/reservas">Ver reservas</Link>
          </li> : null}
        </ul>
        <ul>
          {login ? 
          <li>
            <Link onClick={logout}  to="/">Cerrar sesión</Link>
          </li> : renderMenu(path)}
        </ul>        
        <Redes />
      </div>
    </nav>
  );
}
