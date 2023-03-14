import React, { useState, useEffect } from "react";
import "./atributo.css";
import add from "./add.svg";
import close from "./close.svg";
import urls from '../../../enum/api-urls';
import httpClient from "../../../security/interceptor";
import { set } from "date-fns/esm";

const Atributo = ({setAtributos}) => {
  const [inputs, setInputs] = useState([]);
  const [counter, setCounter] = useState(1);
  const [currentInput, setCurrentInput] = useState({
    nombre: "",
    icon: "",
    id: counter,
  });
  const [caracteristicas, setCaracteristicas] = useState();

  const urlCaract = urls.caracteristicas;

  function addInputs() {
    setCurrentInput({ nombre: "", id: counter, icon: ""});
    setInputs([...inputs, currentInput]);
  }

  const handleInputChange = ({ target }) => {
    const data = {id: counter, ...currentInput}
    data[target.id] = target.value;
    setCurrentInput(data);
    setAtributos({data: data, inputs: inputs})
  };

  const deleteInput = (id) => {
    setInputs(inputs.filter((a) => a.id != id));
  };

  const getCaracteristicas = async () => {
    const response = await httpClient(urlCaract);
    const json = await response.data;
    setCaracteristicas(json.sort(compare));
  };

  // useEffect(() => {
  //   getCaracteristicas();
  // })

  return (
    <>
      {inputs.map((input) => {
        return (
          <div className="atributos" key={input.id}>
            <div className="body-atributos">
              <div className="atributo">
                <label>Nombre</label>
                <input type="text" placeholder="Wifi" defaultValue={input.nombre}/>
              </div>
              <div className="atributo">
                <label>Ícono</label>
                <input type="text" placeholder="fa-wifi" defaultValue={input.icon}/>
              </div>
            </div>

            <img src={close} onClick={() => deleteInput(input.id)}/>
          </div>
        );
      })}

      <div className="atributos">
        <div className="body-atributos">
          <div className="atributo">
            <label>Nombre</label>
            <input type="text" id="nombre" placeholder="Wifi" onChange={handleInputChange} value={currentInput.nombre}/>
          </div>
          <div className="atributo">
            <label>Ícono</label>
            <input type="text" id="icon" placeholder="Url" onChange={handleInputChange}  value={currentInput.icon}/>
          </div>
        </div>

        <img src={add} onClick={addInputs} />
      </div>
    </>
  );
};

export default Atributo;
