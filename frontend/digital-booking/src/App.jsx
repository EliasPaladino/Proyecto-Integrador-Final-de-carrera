import { useState } from "react";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import SignUp from "./pages/signup/SignUp";
import Product from "./pages/product/Product";
import Booking from "./pages/booking/Booking";
import Booked from "./pages/booked/Booked";
import Admin from "./pages/admin/Admin";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "typeface-quicksand";
import { UserContext } from "./components/context/UserContext";
import Success from "./pages/booking/success/Success";
import NoFound from "./pages/noFound/noFound";

//comentario de cami: no es mejor que usemos el react router 5 que es el ultimo que vimos en front? por deduccion me suena a que el utilizado aca es la ultima version
function App() {
  const { user, setUser } = useState({});

  return (
    <BrowserRouter>
      <UserContext.Provider value={{ user, setUser }}>
          <Routes>
            <Route path="/" element={<Navigate to="/home" />} />
            <Route exact path="/home" element={<Home />} />
            <Route exact path="/login" element={localStorage.length==0 ? <Login /> : <Navigate to="/"/>} />
            <Route exact path="/login/:showError" element={localStorage.length==0 ? <Login /> : <Navigate to="/"/>} />
            <Route exact path="/signup" element={localStorage.length==0 ? <SignUp /> : <Navigate to="/"/>} />
            <Route exact path="/admin" element={true ? <Admin /> : <Navigate to="/"/>} />
            <Route exact path="/producto/:id/" element={ <Product />} />         
            <Route exact path="/producto/:id/reserva" element={localStorage.length==0 ? <Navigate to="/"/> : <Booking />} />         
            <Route exact path="/producto/:id/reserva/success" element={localStorage.length==0 ? <Navigate to="/"/> : <Success />}/>
            <Route exact path="/admin/success" element={localStorage.length==0 ? <Navigate to="/"/> : <Success />}/>
            <Route exact path="/reservas" element={<Booked />}/>
            <Route path="*" element={<NoFound />}/>
          </Routes>
      </UserContext.Provider>
    </BrowserRouter>
  );
}

export default App;
