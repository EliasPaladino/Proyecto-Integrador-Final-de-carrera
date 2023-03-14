import React from 'react'
import { FaFacebook, FaInstagram, FaLinkedinIn, FaTwitter } from "react-icons/fa";

export default function Redes() {
  return (
    <div className='redes'>
        <a href="https://www.facebook.com/DigitalBooking6/" target="_blank"><FaFacebook/></a>
        <a href="https://www.linkedin.com/company/digital-booking/" target="_blank"><FaLinkedinIn/></a>
        <a href="https://www.instagram.com/digitalbooking6/" target="_blank"><FaInstagram/></a>
        <a href="https://twitter.com/BookingDigital" target="_blank"><FaTwitter/></a>
    </div>
  )
}
