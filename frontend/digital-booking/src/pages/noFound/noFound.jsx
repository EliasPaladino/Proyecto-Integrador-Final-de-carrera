
import fondo from "../../assets/img/nofound.png";
import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";
import "./noFound.css";

export default function NoFound() {
 

  return (
    <>
        <Header />
            <div className="no-found">
                <img height="100%" src={fondo} />
                <h1>Ups... 404 Página no encontrada</h1>
            </div>
        <Footer />
    </>
  );
}
