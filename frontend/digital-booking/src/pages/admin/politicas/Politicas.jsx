import React, { useState, useEffect } from 'react';
import Input from '../input/Input';
import './politicas.css'


const Politicas = ( {savePoliticas} ) => {
    
    const [politicas, setPoliticas] = useState([]);
    const [politicas1, setPoliticas1] = useState([]);
    const [politicas2, setPoliticas2] = useState([]);
    const [politicas3, setPoliticas3] = useState([]);
    
    const savePolitica1 = (arrayPoliticas) => {
        setPoliticas1(arrayPoliticas);
    }

    const savePolitica2 = (arrayPoliticas) => {
        setPoliticas2(arrayPoliticas);
    }

    const savePolitica3 = (arrayPoliticas) => {
        setPoliticas3(arrayPoliticas);
    }


    useEffect(() => {
        const array = [...politicas1, ...politicas2, ...politicas3]
        setPoliticas(array);
        savePoliticas(array);
    }, [politicas1, politicas2, politicas3]);

    return (
        <div className='politicas'>
            <div className='politicas-body'>
                <h3>Normas de la casa</h3>
                <p>Descripción</p>
                <Input placeHolder={"Ej: No está permitido fumar"} id='1' savePolitica={savePolitica1} handleInputChange/>
            </div>
            <div className='politicas-body'>
                <h3>Salud y seguridad</h3>
                <p>Descripción</p>
                <Input placeHolder={"Ej: Detector de humo"} id='2' savePolitica={savePolitica2}/>
            </div>
            <div className='politicas-body'>
                <h3>Política de cancelación</h3>
                <p>Descripción</p>
                <Input placeHolder={"Ej: Agregá las fechas de tu viaje para obtener los detalles de la cancelación de esta estadía"} id='3' savePolitica={savePolitica3}/>
            </div>
        </div>
    );
}

export default Politicas;
