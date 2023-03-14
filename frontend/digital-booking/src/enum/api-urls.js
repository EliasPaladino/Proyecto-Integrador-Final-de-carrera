
const baseUrl =  "http://grupo6c5digitalbooking-env.eba-8jmz5zun.us-east-2.elasticbeanstalk.com/" //"http://localhost:8080/"
let urls = {
   ciudades: baseUrl + "ciudades",
   caracteristicas: baseUrl + "caracteristicas",
   categorias: baseUrl + "categorias",
   categoriasXProdctos: baseUrl + "productos/categoria/",
   productos: baseUrl + "productos/",
   crearProducto: baseUrl + 'productos/admin',
   login: baseUrl + "login",
   reservas: baseUrl + "reservas",
   reservasXProducto: baseUrl + 'reservas/producto/',
   reservasXUsuario: baseUrl + 'reservas/usuario/',
   signup: baseUrl + 'signup',
   puntuaciones: baseUrl + 'puntuaciones',
   favoritos: baseUrl + 'favoritos'
}
export default urls