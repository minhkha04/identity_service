import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8080/identity/api/', // domain
  timeout: 30000,
  headers: {},
})


// http.interceptors.request.use(
//   function (config) {
//     if (config.skipAuth) {
//       return config;
//     }
//     // lay token tu local
//     const token = localStorage.getItem("token");
//     if (token) {
//       const newToken = JSON.parse(token);
//       config.headers.Authorization = "Bearer " + newToken;
//     }
//     return config;
//   },
//   function (error) {
//     // Do something with request error
//     return Promise.reject(error);
//   }
// );

// Add a response interceptor
http.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export { http }