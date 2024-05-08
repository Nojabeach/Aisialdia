document.addEventListener("DOMContentLoaded", function () {
  const eventsContainer = document.getElementById("events-container");

  cargarActividades();
  cargarEventos();

  const searchForm = document.getElementById("search-form");
  searchForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const searchOption = document.getElementById("search-option").value;
      const searchTerm = document.getElementById("search").value;
      buscarEventos(searchOption, searchTerm);
  });

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

  function cargarEventos() {
      obtenerTodosLosEventosActivos()
          .then((eventos) => {
              mostrarEventos(eventos);
          })
          .catch((error) => {
              console.error("Error al obtener los eventos: ", error);
          });
  }

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

  function buscarEventos(option, term) {
      obtenerTodosLosEventosActivos(
          null,
          option === "fecha" ? term : null,
          option === "ubicacion" ? term : null,
          option === "actividad" ? term : null,
          option === "descripcion" ? term : null
      )
          .then((eventos) => {
              mostrarEventos(eventos);
          })
          .catch((error) => {
              console.error("Error al buscar eventos: ", error);
          });
  }

  function obtenerTodosLosEventosActivos() {
      return fetch('UltimosEventos')
          .then(response => {
              if (!response.ok) {
                  throw new Error('Error al obtener los eventos');
              }
              return response.json();
          })
          .catch(error => {
              throw new Error('Error al obtener los eventos: ' + error.message);
          });
  }
});
