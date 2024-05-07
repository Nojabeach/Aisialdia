/*
Resumen:
1. Carga las actividades posibles desde el servlet GestorActividad y el método visualizarActividades.
2. Llama a la función obtenerTodosLosEventosActivos al cargar la página.
3. Agrega el evento submit al formulario de búsqueda.
4. Muestra los eventos en el contenedor.
5. Busca eventos por actividad.
6. Busca eventos por descripción.
7. Busca eventos por ubicación.
8. Busca eventos por fecha.
*/

document.addEventListener("DOMContentLoaded", function () {
  const eventsContainer = document.getElementById("events-container");

  // Función para cargar las actividades
  function cargarActividades() {
      fetch("GestorActividad?action=visualizarActividades")
          .then((response) => response.json())
          .then((data) => {
              const actividadSelect = document.getElementById("actividad");
              data.forEach((actividad) => {
                  const option = document.createElement("option");
                  option.value = actividad.idActividad;
                  option.textContent = actividad.nombre;
                  actividadSelect.appendChild(option);
              });
          })
          .catch((error) => {
              console.error("Error al cargar las actividades: ", error);
          });
  }

  // Función para cargar los eventos activos
  function cargarEventos() {
      obtenerTodosLosEventosActivos()
          .then((eventos) => {
              mostrarEventos(eventos);
          })
          .catch((error) => {
              console.error("Error al obtener los eventos: ", error);
          });
  }

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

  // Función para buscar eventos
  function buscarEventos(option, term) {
      obtenerTodosLosEventosActivos(null, option === "fecha" ? term : null, option === "ubicacion" ? term : null, option === "actividad" ? term : null, option === "descripcion" ? term : null)
          .then((eventos) => {
              mostrarEventos(eventos);
          })
          .catch((error) => {
              console.error("Error al buscar eventos: ", error);
          });
  }

  // Cargar las actividades al cargar la página
  cargarActividades();

  // Cargar los eventos al cargar la página
  cargarEventos();

  // Agregar evento al formulario de búsqueda
  const searchForm = document.getElementById("search-form");
  searchForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const searchOption = document.getElementById("search-option").value;
      const searchTerm = document.getElementById("search").value;
      buscarEventos(searchOption, searchTerm);
  });

  // Agregar evento al botón de crear evento
  const createEventButton = document.getElementById("create-event-button");
  createEventButton.addEventListener("click", (e) => {
      e.preventDefault();
      const formData = new FormData(document.getElementById("new-event-form"));
      fetch("GestorEvento?action=crearEvento", {
          method: "POST",
          body: formData,
      })
          .then((response) => response.text())
          .then((data) => {
              console.log("Evento creado con éxito");
              // Redirigir a events.html o mostrar un mensaje de éxito
          })
          .catch((error) => {
              console.error("Error al crear evento: ", error);
          });
  });

  // Agregar evento al botón de cerrar sesión
  const logoutButton = document.getElementById("logoutButton");
  logoutButton.addEventListener("click", () => {
      fetch("GestorUsuario?action=cerrarSesion")
          .then((response) => response.text())
          .then(() => {
              window.location.href = "index.html";
          })
          .catch((error) => console.error(error));
  });

});
