import React from 'react'
import { Link, useParams } from 'react-router-dom'

export default function Navbar( { path } ) {
  const id = useParams().id

  const renderMenu = (url) => {
    switch(url) {
      case '/home':
        return (
          <>
            <button><Link to="/signup">Crear cuenta</Link></button>
            <button><Link to="/login">Iniciar sesión</Link></button>
          </>
        )
      case `/producto/${id}`:
        return (
          <>
            <button><Link to="/signup">Crear cuenta</Link></button>
            <button><Link to="/login">Iniciar sesión</Link></button>
          </>
        )
      case '/signup':
        return (
          <>
            <button><Link to="/login">Iniciar sesión</Link></button>
          </>
        )
      case '/login':
        return (
          <>
            <button><Link to="/signup">Crear cuenta</Link></button>
          </>
        )
    }
  }

  return (
    <div className='menu'>
        {renderMenu(path)}
    </div>
  )
}