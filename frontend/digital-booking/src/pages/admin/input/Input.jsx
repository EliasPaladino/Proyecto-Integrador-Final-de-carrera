import React, { useState, useEffect } from "react";
import "./input.css";
import add from "../atributos/add.svg";
import close from "../atributos/close.svg";

const Input = ( { placeHolder, savePolitica = () => null, id, saveImagenes = () => null} ) => {
  const [counter, setCounter] = useState(0);
  const [inputs, setInputs] = useState([]);
  const [politicInputs, setPoliticsInputs] = useState([]);
  const [imagenesInput, setImagenesInput] = useState([]);
  
  const [currentInput, setCurrentInput] = useState({
    valueInput: "",
    id: id,
    key: counter,
  });

  const [politic, setPolitics] = useState( {
    politica: "",
    tipoPolitica: {
      id: id
    }
  });

   const [imagen, setImagen] = useState({})

  function addInputs() {
    setInputs([...inputs, currentInput]);
    setPoliticsInputs([...politicInputs, politic]);
    setImagenesInput([...imagenesInput, imagen]);
    console.log(politicInputs);
    setCurrentInput({ valueInput: "", key: counter });
    setPolitics({
      politica: "",
      tipoPolitica: {
        id: id
      }
    });
    setCounter(counter + 1);
  }

  const handleInputChange = ({ target }) => {
    setCurrentInput({ valueInput: target.value, key: counter, id: id });
    console.log(target)
    if(id == 'imagenes') {
      setImagen({titulo: "titulo", url: target.value, imagenPrincipal: false});
    } else {
      setPolitics({politica: target.value, tipoPolitica: { id: id}});
      savePolitica([...politicInputs, {politica: target.value, tipoPolitica: { id: id}}]);
    }
  };

  const deleteInput = (id) => {
    setInputs(inputs.filter((a) => a.key != id));
  };
  
  useEffect(() => {
    saveImagenes(imagenesInput);
  }, [inputs, politicInputs])
  return (
    <>
      {inputs.map((input) => {
        return (
          <div className="input-admin" key={input.key}>
            <input type="text" defaultValue={input.valueInput}/>
            <img src={close} onClick={() => deleteInput(input.key)} />
          </div>
        );
      })}

      <div className="input-admin">
        <input
          type="text"
          onChange={handleInputChange}
          value={currentInput.valueInput}
          placeholder={placeHolder}
          
        />
        {/* <label className="control control-checkbox">
          Imagen Principal
          <input type="checkbox" />
          <div className="control_indicator"></div>
        </label> */}
        <img src={add} onClick={addInputs} />
      </div>
    </>
  );
};

export default Input;
