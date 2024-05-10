window.onload = function () {
  console.log("Cargando Actividades");
  obtenerActividades();
};

function obtenerActividades() {
  fetch('GestorActividad?action=visualizarActividades')
      .then(response => response.json())
      .then(data => pintarSelect(data))
}

function pintarSelect(datos) {
  let select = document.getElementById("activity");
  let image = document.getElementById("activity-image");

  datos.forEach((actividad, index) => {
      let option = document.createElement("option");
      option.value = actividad.idActividad;
      option.textContent = actividad.tipoActividad;

      // Guardar idActividad, tipoActividad y fotoActividad en atributos personalizados
      option.setAttribute("data-id", actividad.idActividad);
      option.setAttribute("data-tipo", actividad.tipoActividad);
      option.setAttribute("data-foto", actividad.fotoActividad);
      select.appendChild(option);

      // Mostrar la primera imagen por defecto si es la primera opción
      if (index === 0) {
          image.src = `img/Iconos/${actividad.tipoActividad.toLowerCase()}.png`;
          image.alt = `Icono ${actividad.tipoActividad}`;
          image.style.display = "block";
      }
  });

  // Agregar evento change al select
  select.addEventListener('change', function () {
      let selectedOption = this.options[this.selectedIndex];
      let fotoActividad = selectedOption.getAttribute("data-foto");

      // Mostrar la imagen seleccionada
      if (fotoActividad) {
         // image.src = fotoActividad;
          image.src = `img/Iconos/${selectedOption.getAttribute("data-tipo").toLowerCase()}.png`;
          image.alt = `Icono ${selectedOption.textContent}`;
          image.style.display = "block";
      } else {
          // Mostrar la primera imagen si no hay imagen seleccionada
          image.src = `img/Iconos/${selectedOption.getAttribute("data-tipo").toLowerCase()}.png`;
          image.alt = `Icono ${selectedOption.textContent}`;
          image.style.display = "block";
      }
  });
}
