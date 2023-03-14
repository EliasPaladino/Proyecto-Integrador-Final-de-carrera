import axios from "axios";
import errorInterceptor from "./errorInterceptor";
const httpClient = axios.create({
baseURL: process.env.REACT_APP_API_URL,
});
errorInterceptor(httpClient);
export default httpClient;