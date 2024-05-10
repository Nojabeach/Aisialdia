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
    let searchOption = document.getElementById("search-option").value;
    busquedaEventos(searchOption);
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

function busquedaEventos(criterio) {
  fetch(
    "GestorEvento?action=obtenerTodosLosEventosActivos&criterio=" + criterio
  )
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("Buscar-tabla");
      contenedorAPintar.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos
      pintarTabla(data, contenedorAPintar); // Pintar la tabla
      console.log("Pintando eventos buscados");
    });
}
