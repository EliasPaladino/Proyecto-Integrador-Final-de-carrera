import React, { useEffect, useState } from "react";
import "./home.css";
import "../../components/header/header.css";
import "../../components/footer/footer.css";
import "../../components/footer/redes/redes.css";
import Header from "../../components/header/Header";
import Footer from "../../components/footer/Footer";
import Searcher from "./searcher/Searcher";
import Card from "./card/Card";
import Recommendations from "./recommendations/Recommendations";
import { format } from "date-fns";
import urls from "../../enum/api-urls";
import httpClient from "../../security/interceptor";

function getRandom() {
  var aleatorio = Math.random() * (3 - 0) + 0;
  aleatorio = Math.round(aleatorio);
  return aleatorio;
}


export default function Home() {
  const productos = urls.productos;
  const mainUrl = productos + "listar";
  const [products, setProducts] = useState([]);
  let [isloaded, setLoaded] = useState(false);
  let [counter, setCounter] = useState(0);

  const filters = (filter) => {
    let productosUrl = productos;
    let productosFecha = productos;
    if(filter){ 
      if(filter.city && !(filter.date && filter.date.length)){
        productosUrl = `${productosFecha}ciudad/${filter.city}`; 
      } else if(filter.city && filter.date && filter.date.length){
        productosUrl =`${productosFecha}resultados/ciudad/${filter.city}?fechaInicio=${format(filter.date[0].startDate, 'yyyy-MM-dd')}&fechaFin=${format(filter.date[0].endDate, 'yyyy-MM-dd')}`;
      } else if(!filter.city && filter.date && filter.date.length){
        productosUrl = `${productosFecha}fechas?fechaInicio=${format(filter.date[0].startDate, 'yyyy-MM-dd')}&fechaFin=${format(filter.date[0].endDate, 'yyyy-MM-dd')}`;
      } else {
        productosUrl = mainUrl;
      }  
    }    
    httpClient(productosUrl)
        .then((response) => response.data)
        .then((datos) => {
          setProducts(
            datos.map((r) => {
              const fotoPrincipal = r.imagenes && r.imagenes.length ? r.imagenes.find(i => i.fotoPrincipal)?.url : null;
              r.image = fotoPrincipal? fotoPrincipal: (r.imagenes && r.imagenes.length ?r.imagenes[0].url: null);
              return r;
            })
          );
          const c = counter + 1;
          setCounter(c);
          if(c > 1){
            document.getElementById('recommendations-section').scrollIntoView({
              behavior: 'smooth'
            });
          }
        }, error => {
          setProducts([])
        });
  }

  const categorySelected = async (catSelected) => {
    if(!isloaded){
      let url = mainUrl;
      if(catSelected) url = urls.categoriasXProdctos + catSelected;
      httpClient(url)
          .then((response) => response.data)
          .then((datos) => {
            setLoaded(false);
            setProducts(
              datos.map((r) => {
                const fotoPrincipal = r.imagenes && r.imagenes.length ? r.imagenes.find(i => i.fotoPrincipal)?.url : null;
                r.image = fotoPrincipal? fotoPrincipal: (r.imagenes && r.imagenes.length ?r.imagenes[0].url: null);
                r.stars = getRandom();
                return r;
              })
            );
            const c = counter + 1;
            setCounter(c);
            if(c > 1){
              document.getElementById('recommendations-section').scrollIntoView({
                behavior: 'smooth'
              });
            }
          }, error => {
            setLoaded(false);
          });
    }
  }

  useEffect(() => {
    if(!isloaded){
      setLoaded(true);
      categorySelected();
    }
  }, []);
  return (
    <>
      <Header />

      <main className="mt-93">
          <Searcher filters={filters} />
          <Card categorySelected={ categorySelected }/>
          <Recommendations products={products}/>
      </main>

      <Footer />
    </>
  );
}
