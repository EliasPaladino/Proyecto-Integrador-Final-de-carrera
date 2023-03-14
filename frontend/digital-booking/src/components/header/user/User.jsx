import React, { useState } from 'react'
import { FaThList, FaDoorOpen, FaUserAlt } from 'react-icons/fa';
import './user.css'

const User = ( { logout, activarMenu } ) => {

  const nombre = localStorage.getItem('nombre');
  const apellido = localStorage.getItem('apellido');
  const [showMenu, setShowMenu] = useState(false);

  const menu = () => { 
    if(activarMenu === false){
      setShowMenu(!showMenu)
    }
  }
  const rol = localStorage.getItem('rol');
    
  return (
    <div className='user-info'>
        <div className='user-logo' onClick={menu}>
          <p>{nombre.charAt(0)}{apellido.charAt(0)}</p>
        </div>
        <div className={`user-popover ${showMenu ? "" : "hide"}`}>
          {rol == "ROLE_ADMIN" ? <button onClick={() => window.location.href = "/admin"}><FaUserAlt className='cerrar-sesion'></FaUserAlt>Administrador</button> : null}
          <button onClick={() =>window.location.href = "/reservas" }><FaThList className='cerrar-sesion' ></FaThList>Ver reservas</button>
          <button onClick={logout}><FaDoorOpen className='cerrar-sesion'></FaDoorOpen>Cerrar sesión</button>
        </div>
        <div className='user-welcome'>
            <p>Hola,</p>
            <p className='user-name'>{nombre} {apellido}</p>
        </div>
    </div>
  )
}

export default User