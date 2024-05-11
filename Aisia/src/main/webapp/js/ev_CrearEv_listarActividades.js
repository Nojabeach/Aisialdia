window.onload = function () {
  console.log("Cargando Actividades");
  obtenerActividades();
};

function obtenerActividades() {
  console.log("Obteniendo Actividades");
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Hubo un problema al obtener los datos.");
      }
      console.log("Actividades obtenidas correctamente");
      return response.json();
    })
    .then((data) => pintarSelect(data))
    .catch((error) => console.error("Error al obtener las actividades:", error));
}

function pintarSelect(datos) {
  let select = document.getElementById("activity");
  let image = document.getElementById("activity-image");

  select.innerHTML = ""; // Limpiar el select antes de agregar nuevas opciones

  datos.forEach(actividad => {
    let option = document.createElement("option");
    option.value = actividad.idActividad;
    option.textContent = actividad.tipoActividad;

    // Guardar idActividad, tipoActividad y fotoActividad en atributos personalizados
    option.setAttribute("data-id", actividad.idActividad);
    option.setAttribute("data-tipo", actividad.tipoActividad);
    option.setAttribute("data-foto", actividad.fotoActividad);

    select.appendChild(option);
  });

  // Mostrar la primera imagen por defecto si es la primera opción
  mostrarImagen(image, select.options[0]);

  // Agregar evento change al select
  select.addEventListener("change", function () {
    let selectedOption = this.options[this.selectedIndex];
    mostrarImagen(image, selectedOption);
  });
}

function mostrarImagen(image, option) {
  let fotoActividad = option.getAttribute("data-foto");
  let tipoActividad = option.getAttribute("data-tipo").toLowerCase();
  let actividadText = option.textContent;

  let imagenSrc =  `img/Iconos/${tipoActividad}.png`;
  image.src = imagenSrc;
  image.style.display = "block";
}
