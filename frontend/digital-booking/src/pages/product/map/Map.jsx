import React from "react";
import "./map.css";
import "leaflet/dist/leaflet.css";
import location from "../../../assets/img/icons/location.svg";
import L from "leaflet";
import { MapContainer, Marker, TileLayer, Popup } from "react-leaflet";

const Map = ({ ciudad, pais, lat, long, titulo }) => {
  return (
    <div className="container map-container">
      <h2>¿Dónde vas a estar?</h2>
      <hr />
      <p>
        {ciudad}, {pais}
      </p>
      <div id="map">
        <MapContainer center={[lat, long]} zoom={13}>
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          <Marker
            position={[lat, long]}
            icon={L.icon({
              iconUrl: location,
              iconRetinaUrl: location,
              iconSize: [30, 30],
              shadowUrl: null,
              shadowSize: null,
              shadowArchor: null,
              className: "leaflet-venue-icon",
            })}
          >
            <Popup><h3>{titulo}</h3></Popup>
          </Marker>
        </MapContainer>
      </div>
    </div>
  );
};

export default Map;
