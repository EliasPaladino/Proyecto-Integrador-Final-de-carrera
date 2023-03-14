import React from "react";
import { Link } from "react-router-dom";
import "./productHeader.css";
import arrow from "../../../assets/img/producto/arrow.svg";

export default function ProductHeader({ categoria, titulo }) {

  const category = categoria?.toUpperCase();

  return (
    <div className="product-header-container mt-93">
      <div className="product-header container">
        <div className="product-header__info">
          <p>{category}</p>
          <h2>{titulo}</h2>
        </div>

        <Link to="/">
          <img src={arrow} alt="arrow" className="arrow" />
        </Link>
      </div>
    </div>
  );
}
