import React from 'react'

const Alert = (alertActivo) => {
  return (
    <div className={ alertActivo ? 'carousel-template' : '' }>
        <div className='alert'>

        </div>
    </div>
  )
}

export default Alert