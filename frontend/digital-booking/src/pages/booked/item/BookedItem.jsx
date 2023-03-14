import { addDays, format } from 'date-fns';
import React from 'react'
import { FaExpand, FaMapMarkerAlt } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import "../booked.css";

const BookedItem = ({reservas}) => {
    
    const obtenerFecha = (fecha) => {
        const fechaResultante = new Date(fecha[0], fecha[1]-1, fecha[2])
        return format(fechaResultante, 'dd-MM-yyyy');
    };
    function compare(a, b) {
        const bandA = new Date(a.fechaInicio[0], a.fechaInicio[1], a.fechaInicio[2]).getTime()
        const bandB = new Date(b.fechaInicio[0], b.fechaInicio[1], b.fechaInicio[2]).getTime()
        return bandB - bandA;
    }
    reservas.sort(compare);

    return (
        <div  className='tabla-container'>
            <table className='tabla-reserva'>
                <tr className='tabla-header'>
                    <th className='tabla-reserva-item'>Hora Ingreso</th>
                    <th className='tabla-reserva-item'>Producto</th>
                    <th className='tabla-reserva-phone'>Productos</th>
                    <th className='tabla-reserva-item'>Fecha Inicio</th>
                    <th className='tabla-reserva-item'>Fecha Fin</th>
                    <th className='tabla-reserva-item'>Ubicacion</th>
                    <th className='tabla-reserva-item'>Ver</th>
                </tr>
                {reservas.length > 0?reservas.map((reserva) => (
                    <tr key={reserva.id}>
                        <td className='tabla-reserva-item'>
                            <span>{reserva.horaIngreso}</span>
                        </td>
                        <td className='tabla-reserva-item'>
                            <span>{reserva.producto?.titulo}</span>
                        </td>
                        <td className='tabla-reserva-item'>
                            <span>{obtenerFecha(reserva.fechaInicio)}</span>
                        </td>
                        <td className='tabla-reserva-item'>
                            <span>{obtenerFecha(reserva.fechaFin)}</span>
                        </td>
                        <td className='tabla-reserva-item'>
                            <FaMapMarkerAlt className='tabla-reserva-icono red' onClick={() =>
                            window.open(
                                `https://www.google.com/maps/search/${reserva.producto?.latitud},${reserva.producto?.longitud}`,
                                "_blank"
                            )
                            }></FaMapMarkerAlt>
                            <span>{reserva.producto?.ubicacion?.nombre}({reserva.producto?.ubicacion?.pais})</span>
                            
                        </td>
                        <td className='tabla-reserva-item'>
                            <span>
                            <Link
                                type="button"
                                to={"/producto/" + reserva.producto?.id}
                            >
                                <FaExpand className='tabla-reserva-icono'></FaExpand>
                            </Link>
                            </span>
                        </td>
                        
                        <td className='tabla-reserva-phone'>
                            <span className='tabla-reserva-title'>
                                {reserva.producto?.titulo}
                                <Link
                                    type="button"
                                    to={"/producto/" + reserva.producto?.id}
                                >
                                    <FaExpand className='tabla-reserva-icono'></FaExpand>
                                </Link>
                            </span>
                            <span>
                                <FaMapMarkerAlt className='tabla-reserva-icono red' onClick={() =>
                                window.open(
                                    `https://www.google.com/maps/search/${reserva.producto?.latitud},${reserva.producto?.longitud}`,
                                    "_blank"
                                )
                                }></FaMapMarkerAlt>
                                    {reserva.producto?.ubicacion?.nombre}({reserva.producto?.ubicacion?.pais})
                            </span>
                            
                            <span>({reserva.horaIngreso}) {obtenerFecha(reserva.fechaInicio)} - {obtenerFecha(reserva.fechaFin)}</span>
                        </td>
                    </tr>
                )): <h4>Usted no posee reservas</h4>}
            </table>
        </div>
        
    )
};

export default BookedItem