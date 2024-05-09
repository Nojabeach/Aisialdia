// Llamar a la función al cargar la página
document.addEventListener('DOMContentLoaded', function() {

    console.log("Cargando Favoritos");
    obtenerFavoritos();

    alert("Cargando eventos activos");
    obtenerTodosLosEventosActivos();

    alert( "Cargando busqueda de eventos");
    let searchButton = document.getElementById("search-button");
    searchButton.addEventListener("click", function(event) {
        event.preventDefault(); // Evita que se envíe el formulario
        let searchOption = document.getElementById("search-option").value;
        busquedaEventos(searchOption);
    });


  });


  function obtenerFavoritos() {
    fetch('GestorFavorito?action=obtenerEventosFavoritos')
    .then(response => response.json())
  
    .then(data => {
      console.log(data);
        const contenedorAPintar = document.getElementById('favoritos-tabla');
        pintarTablaSoloBorrar(data, contenedorAPintar);
        console.log("Pintando favoritos");  
    })
  }
  
function obtenerTodosLosEventosActivos(){
    fetch('GestorEventos?action=obtenerTodosLosEventosActivos')
        .then(response => response.json())
        .then(data => {
            const contenedorAPintar = document.getElementById('Publicados-tabla');
            contenedorAPintar.innerHTML = ''; // Limpiar el contenedor antes de agregar nuevos elementos
            pintarTabla(data, contenedorAPintar); // Pintar la tabla
        });
  }

  function busquedaEventos(criterio){
		
    fetch('GestorEvento?action=obtenerTodosLosEventosActivos&criterio='+criterio)
        .then(response => response.json())
        .then(data => {
            const contenedorAPintar = document.getElementById('Buscar-tabla');
            contenedorAPintar.innerHTML = ''; // Limpiar el contenedor antes de agregar nuevos elementos
            pintarTabla(data, contenedorAPintar); // Pintar la tabla
        });
    
}