
  document.addEventListener('DOMContentLoaded', function() {
    console.log("Cargando Actividades");
    EDITobtenerActividades();
  });
  function EDITobtenerActividades() {
    console.log("Obteniendo Actividades para el select de Editar Actividad");
    fetch("GestorActividad?action=visualizarActividades")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Hubo un problema al obtener los datos.");
        }
        console.log("Actividades obtenidas correctamente");
        return response.json();
      })
      .then((data) => EDITpintarSelect(data))
      .catch((error) => console.error("Error al obtener las actividades:", error));
  }
  
  function EDITpintarSelect(datos) {
    let select = document.getElementById("EDITactivity");
    let image = document.getElementById("EDITactivity-image");
  
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
    EDITmostrarImagen(image, select.options[0]);
  
    // Agregar evento change al select
    select.addEventListener("change", function () {
      let selectedOption = this.options[this.selectedIndex];
      EDITmostrarImagen(image, selectedOption);
    });
  }
  
  function EDITmostrarImagen(image, option) {
    let fotoActividad = option.getAttribute("data-foto");
    let tipoActividad = option.getAttribute("data-tipo").toLowerCase();
    let actividadText = option.textContent;
  
    let imagenSrc =  `img/Iconos/${tipoActividad}.png`;
    image.src = imagenSrc;
    image.style.display = "block";
  }
  