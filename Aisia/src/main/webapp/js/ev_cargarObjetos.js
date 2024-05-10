// Llamar a la función al cargar la página
document.addEventListener("DOMContentLoaded", function () {
  console.log("Cargando Favoritos");
  obtenerFavoritos();

  console.log("Cargando eventos activos");
  obtenerTodosLosEventosActivos();

  console.log("Cargando busqueda de eventos");
  let searchButton = document.getElementById("search-button");
  searchButton.addEventListener("click", function (event) {
    event.preventDefault(); // Evita que se envíe el formulario
    busquedaEventos();
  });
});

function obtenerFavoritos() {
  fetch("GestorFavorito?action=obtenerEventosFavoritos")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("favoritos-tabla");
      pintarTablaSoloBorrar_Favorito(data, contenedorAPintar);
      console.log("Pintando favoritos");
    });
}

function obtenerTodosLosEventosActivos() {
  fetch("GestorEvento?action=obtenerTodosLosEventosActivos")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("Publicados-tabla");
      contenedorAPintar.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos
      pintarTabla(data, contenedorAPintar); // Pintar la tabla
      console.log("Pintando eventos activos");
    });
}

function busquedaEventos() {
  const criterio = document.getElementById("search-option").value;
  const textoBusqueda = document.getElementById("search").value;

alert("Buscando eventos por criterio: " + criterio);
alert("Buscando eventos por texto: " + textoBusqueda);
  fetch(
      `GestorEvento?action=obtenerTodosLosEventosActivos&criterio=${criterio}&textoBusqueda=${textoBusqueda}`
  )
  .then((response) => response.json())
  .then((data) => {
      console.log("Buscando eventos por criterio: " + criterio);
      const contenedorAPintar = document.getElementById("Buscar-tabla");
      contenedorAPintar.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos
      pintarTabla(data, contenedorAPintar); // Pintar la tabla
      console.log("Pintando eventos buscados");
  });
}
