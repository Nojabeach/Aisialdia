
import { pintarTabla } from './pintarTabla.js';


// Llamar a la función al cargar la página
window.onload = function () {
    obtenerFavoritos();
  };
  
  function  obtenerFavoritos(){

    fetch('GestorFavorito?action=obtenerEventosFavoritos')
    .then(response => response.json())
    .then(data => pintarTabla(data))
  };