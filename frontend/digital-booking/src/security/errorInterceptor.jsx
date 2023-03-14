
import swal from 'sweetalert';
import urls from '../enum/api-urls';
const errorInterceptor = (axiosInstance) => {
axiosInstance.interceptors.response.use((response) => {
  //Response Successful
  const token = window.localStorage.getItem('jwt');
  const tiempoToken = parseInt(window.localStorage.getItem('tiempoToken'));
  const email = window.localStorage.getItem('email');
  const tiempoRecargaToken = 4 * 60 * 60 * 1000;
  if( tiempoToken && tiempoToken + tiempoRecargaToken < new Date().getTime()) {  
    const urlLogin = urls.login;
    const contrasenia = "proyecto"
    fetch(urlLogin, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
         {
          email: email,
          contrasenia: contrasenia,
        }
      ),
    })
      .then((res) => res.json())
      .then((result) => {
        console.log(result)
        window.localStorage.setItem("email", email);
        window.localStorage.setItem("nombre", result.nombre);
        window.localStorage.setItem("apellido", result.apellido);
        window.localStorage.setItem("jwt", "Bearer " + result.jwt);
        window.localStorage.setItem("tiempoToken", new Date().getTime());
        window.localStorage.setItem("usuarioId", result.usuarioId);
        window.location.href = "/";
      }, e=> {
      });
  }
  return response
},(error) => {
  if (error?.response?.status === 403) {
    //Unauthorized
    //redirect to Login
    swal('Tiempo de sesión finalizado, por favor loguee nuevamente')
    window.localStorage.clear();
    window.location.href = "/login";
  } else {
    console.log(error);
  }
});
};
export default errorInterceptor;