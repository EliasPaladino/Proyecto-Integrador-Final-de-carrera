import React, { Fragment, useEffect, useState, Component } from "react";
import { FaTrashAlt, FaMapMarkerAlt } from "react-icons/fa";
import  {ReactComponent as ReactLogo} from "../../../assets/icons/datepicker.svg";
import urls from "../../../enum/api-urls";
import "./searcher.css";
import Autocomplete from "./autocomplete/Autocomplete";
import { DateRange, DateRangePicker } from "react-date-range";
import { addDays, format } from "date-fns";
import * as locales from 'react-date-range/dist/locale';
import { es } from 'date-fns/locale'
import httpClient from "../../../security/interceptor";


function Searcher({filters}) {
  const labelPlaceholder = "Check in - Check out";
  const [todos, setTodos] = useState();
  const [placeholder, setPlaceholder] = useState(labelPlaceholder);
  const [locale, setLocale] = React.useState('es');
  const [dateFilter, setDateFilter] = useState(false);
  const [autocompleteData, setAutocompleteData] = useState();

  
  const orientation = window.matchMedia("(max-width: 1100px)").matches ? 1 : 2;
  
  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
      color: '#1DBEB4',
      counter: 0,
    },
  ]);

  function compare( a, b ) {
    if ( a.nombre < b.nombre ){
      return -1;
    }
    if ( a.nombre > b.nombre ){
      return 1;
    }
    return 0;
  }

  function formatDate (date){
    return format(date, "d 'de' MMM yyyy.", {
      locale: es
    })
  }

  const changeDate = (reason, item) => {
    if (reason[0].counter === 0) {
      reason[0].counter++;
    } else {
      reason[0].counter = 0;
      setPlaceholder(formatDate(reason[0].startDate) + " - " +  formatDate(reason[0].endDate) );
      setDateFilter(true)
      setClass("datepicker hide");
      window.sessionStorage.setItem('startDate', reason[0].startDate)
      window.sessionStorage.setItem('endDate', reason[0].endDate)
    }
    setDate(reason)
  };

  const [dateClass, setClass] = useState("datepicker hide");
  const [city, setCity] = useState();

  var inputClass = dateClass;
  const urlCiudades = urls.ciudades;

  const fetchApi = async () => {
    const response = await httpClient(urlCiudades);
    const json = await response.data;
    setTodos(json.sort( compare ));
  };

  const handleAutocomplete  = async (value) => {
    setCity(value);
  }

  const clearDate = () => { 
    setDateFilter(false)
    setPlaceholder(labelPlaceholder);
    window.sessionStorage.clear()
    setDate([
      {
        startDate: new Date(),
        endDate: new Date(),
        key: "selection",
        color: '#1DBEB4',
        counter: 0,
      },
    ])
  }

  const setFilters = () => { 
    const filter = { city };
    if(dateFilter){
      filter.date = date;
    } 
    filters(filter)
  }

  useEffect(() => {
    fetchApi();
    window.sessionStorage.clear()
  }, []);

  return (
    <section className="searcher">
      <div className="container-searcher">
        <h1>Busca ofertas en casas, departamentos y mucho más </h1>
        <form>
          <section className="input-icons">
            <FaMapMarkerAlt></FaMapMarkerAlt>
            <Autocomplete
              suggestions={
                !todos
                  ? null
                  : todos.map((item) => {
                      return {
                        title: item.nombre,
                        id: item.id,
                        subtitle: item.pais,
                        icono: true
                      };
                    })
              }
              handleChanges={handleAutocomplete}
            />
          </section>
          <section className="input-icons datepicker-container">
            <ReactLogo onClick={() => setClass("datepicker show")} />

            <Fragment>
              <input
                type="text"
                onFocus={() => setClass("datepicker show")}
                className="input-datepicker searcher-inputs"
                placeholder={placeholder}
              ></input>
            </Fragment>
            <FaTrashAlt  className={`clearDate ${dateFilter ? "" : "hide"}`} onClick={() => clearDate()}></FaTrashAlt>
           
            <DateRangePicker
              locale={locales[locale]}
              minDate={new Date()}
              editableDateInputs={false}
              onChange={(item) => changeDate([item.selection], item)}
              moveRangeOnFirstSelection={true}
              ranges={date}
              months={orientation}
              showDateDisplay={false}
              className={inputClass}
              showMonthAndYearPickers={false}
              direction="horizontal"
            />
          </section>
          <section className="input-icons">
            <button className="searcher-button" type="button"  onClick={() => setFilters()}>
              {" "}
              Buscar{" "}
            </button>
          </section>
        </form>
      </div>
    </section>
  );
}

export default Searcher;
