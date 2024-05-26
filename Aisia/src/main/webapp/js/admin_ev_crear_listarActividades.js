window.onload = function () {
  console.log('Script admin_ev_crear_listarActividades cargado');
  EVcrearobtenerActividades();
};

function EVcrearobtenerActividades() {
  console.log("Obteniendo Actividades para el select de Crear Actividad");
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Hubo un problema al obtener los datos.");
      }
      console.log("Actividades obtenidas correctamente");
      return response.json();
    })
    .then((datos) => EVcrearpintarSelect(datos))
    .catch((error) => console.error("Error al obtener las actividades:", error));
}

function EVcrearpintarSelect(datos) {
  let select = document.getElementById("EVactivity");
  let image = document.getElementById("EVactivity-image");

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
  EVcrearMostrarImagen(image, select.options[0]);

  // Agregar evento change al select
  select.addEventListener("change", function () {
    let selectedOption = this.options[this.selectedIndex];
    EVcrearMostrarImagen(image, selectedOption);
  });
}

function EVcrearMostrarImagen(image, option) {
  let fotoActividad = option.getAttribute("data-foto");
  let tipoActividad = option.getAttribute("data-tipo").toLowerCase();
  let actividadText = option.textContent;

  // Cargar la imagen correspondiente cuando se selecciona una opción
  let img = new Image();
  img.src = `img/Iconos/${tipoActividad}.png`;
  img.style.display = "none";
  image.parentNode.insertBefore(img, image);
  img.onload = function() {
    image.src = this.src;
    image.style.display = "block";
    img.style.display = "none";
  };
}