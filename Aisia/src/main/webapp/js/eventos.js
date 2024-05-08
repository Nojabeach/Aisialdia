window.onload = function () {
  cargarEventos();
};

const eventsContainer = document.getElementById("events-container");
const searchForm = document.getElementById("search-form");
searchForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const searchOption = document.getElementById("search-option").value;
  const searchTerm = document.getElementById("search").value;
  buscarEventos(searchOption, searchTerm);
});

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

function obtenerUltimosEventos() {
  console.log("Obteniendo últimos eventos...");
  fetch("UltimosEventos")
    .then((response) => {
      console.log("Respuesta del servidor:", response);
      if (response.ok) {
        return response.json();
      } else {
        throw new Error("Error al obtener los datos del servlet");
      }
    })
    .then((data) => {
      console.log("Datos obtenidos:", data);
      const eventosList = document.getElementById("ultimosEventosList");
      eventosList.innerHTML = "";
      data.forEach((evento) => {
        console.log("Evento:", evento);
        const li = document.createElement("li");
        const img = document.createElement("img");
        img.src = `img/Iconos/${evento.tipoActividad.toLowerCase()}.png`;
        console.log("img.src:", img.src);
        img.alt = `Icono ${evento.tipoActividad}`;
        const a = document.createElement("a");
        a.href = "#";
        const fecha = new Date(evento.fechaEvento);
        const fechaFormateada = `${fecha.getDate()}/${
          fecha.getMonth() + 1
        }/${fecha.getFullYear()}`;
        a.textContent = `${evento.nombre} - ${fechaFormateada}`;
        li.appendChild(img);
        li.appendChild(a);
        eventosList.appendChild(li);
      });
    })
    .catch((error) => console.error(error));
}
