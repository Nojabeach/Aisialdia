import { pintarTabla } from './pintarTabla.js';


// Llamar a la función al cargar la página
window.onload = function () {
    obtenerTodosLosEventosActivos();
  };
  
  function  obtenerTodosLosEventosActivos(){

    fetch('GestorEventos?action=obtenerTodosLosEventosActivos')
    .then(response => response.json())
    .then(data => pintarTabla(data))
  };