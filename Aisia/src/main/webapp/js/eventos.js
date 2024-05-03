/*
Resumen:
1. Carga las actividades posibles desde el servlet GestorActividad y el método visualizarActividades.
2. Agrega evento al botón de cerrar sesión.
3. Llama a la función obtenerTodosLosEventosActivos al cargar la página.
4. Agrega el evento submit al formulario de búsqueda.
5. Muestra los eventos en el contenedor.
6. Busca eventos por actividad.
7. Busca eventos por descripción.
8. Busca eventos por ubicación.
9. Busca eventos por fecha.
*/
document.addEventListener("DOMContentLoaded", function () {
  // Cargar las actividades posibles desde el servlet GestorActividad y el método visualizarActividades
  fetch("GestorActividad?accion=visualizarActividades")
    .then((response) => response.json())
    .then((data) => {
      // Crear un option para cada actividad y añadirlo al select de actividades
      const actividadSelect = document.getElementById("actividad");
      data.forEach((actividad) => {
        const option = document.createElement("option");
        option.value = actividad.idActividad;
        option.textContent = actividad.nombre;
        actividadSelect.appendChild(option);
      });
    });
});

// Agregar evento al botón de cerrar sesión
const logoutButton = document.getElementById("logoutButton");
logoutButton.addEventListener("click", () => {
  // Llamar al servlet para cerrar sesión
  fetch("GestorUsuario?action=cerrarSesion")
    .then((response) => response.text())
    .then((data) => {
      // Redirigir a index.html
      window.location.href = "index.html";
    })
    .catch((error) => console.error(error));
});

document.addEventListener("DOMContentLoaded", () => {
  const eventsContainer = document.getElementById("events-container");

  // Llamar a la función obtenerTodosLosEventosActivos al cargar la página
  obtenerTodosLosEventosActivos()
    .then((eventos) => {
      mostrarEventos(eventos);
    })
    .catch((error) => {
      console.error("Error al obtener los eventos: ", error);
    });

  // Agregar el evento submit al formulario de búsqueda
  const searchForm = document.getElementById("search-form");
  searchForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const searchOption = document.getElementById("search-option").value;
    const searchTerm = document.getElementById("search").value;

    // Realizar la búsqueda según la opción seleccionada
    switch (searchOption) {
      case "actividad":
        buscarEventosPorActividad(searchTerm);
        break;
      case "descripcion":
        buscarEventosPorDescripcion(searchTerm);
        break;
      case "ubicacion":
        buscarEventosPorUbicacion(searchTerm);
        break;
      case "fecha":
        buscarEventosPorFecha(searchTerm);
        break;
      default:
        console.error("Opción de búsqueda no válida");
    }
  });
});

// Función para mostrar los eventos en el contenedor
function mostrarEventos(eventos) {
  eventsContainer.innerHTML = "";
  eventos.forEach((evento) => {
    const eventoDiv = document.createElement("div");
    eventoDiv.classList.add("evento");

    const detalle = document.createElement("div");
    detalle.classList.add("evento-detalle");
    eventoDiv.appendChild(detalle);

    const nombre = document.createElement("h3");
    nombre.textContent = evento.nombre;
    detalle.appendChild(nombre);

    const detallesParrafo = document.createElement("p");
    detallesParrafo.textContent = evento.detalles;
    detalle.appendChild(detallesParrafo);

    const etiquetasDiv = document.createElement("div");
    etiquetasDiv.classList.add("etiquetas");
    evento.etiquetas.forEach((etiqueta) => {
      const etiquetaSpan = document.createElement("span");
      etiquetaSpan.classList.add("etiqueta");
      etiquetaSpan.textContent = etiqueta;
      etiquetasDiv.appendChild(etiquetaSpan);
    });
    detalle.appendChild(etiquetasDiv);

    const miniMapa = document.createElement("div");
    miniMapa.classList.add("mini-mapa");
    eventoDiv.appendChild(miniMapa);

    eventsContainer.appendChild(eventoDiv);
  });
}

// Función para buscar eventos por actividad
function buscarEventosPorActividad(actividad) {
  obtenerTodosLosEventosActivos(actividad, null, null, null)
    .then((eventos) => {
      mostrarEventos(eventos);
    })
    .catch((error) => {
      console.error("Error al buscar eventos por actividad: ", error);
    });
}

// Función para buscar eventos por descripción
function buscarEventosPorDescripcion(descripcion) {
  obtenerTodosLosEventosActivos(null, descripcion, null, null)
    .then((eventos) => {
      mostrarEventos(eventos);
    })
    .catch((error) => {
      console.error("Error al buscar eventos por descripción: ", error);
    });
}

// Función para buscar eventos por ubicación
function buscarEventosPorUbicacion(ubicacion) {
  obtenerTodosLosEventosActivos(null, null, ubicacion, null)
    .then((eventos) => {
      mostrarEventos(eventos);
    })
    .catch((error) => {
      console.error("Error al buscar eventos por ubicación: ", error);
    });
}

// Función para buscar eventos por fecha
function buscarEventosPorFecha(fecha) {
  obtenerTodosLosEventosActivos(null, null, null, fecha)
    .then((eventos) => {
      mostrarEventos(eventos);
    })
    .catch((error) => {
      console.error("Error al buscar eventos por fecha: ", error);
    });
}