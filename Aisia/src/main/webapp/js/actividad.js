// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener("DOMContentLoaded", function () {
  // Muestra un mensaje para verificar que el DOM está cargado
  console.log("DOM cargado");

  // Obtiene el elemento de la subnavbar y la oculta inicialmente
  const subnavbar = document.getElementById("subnavbar");
  subnavbar.style.display = "none";

  // Obtiene el nombre del archivo actual (por ejemplo, actividad.html)
  var currentPage = window.location.pathname.split("/").pop().split(".")[0];

  // Obtiene el enlace correspondiente en la navbar
  var currentLink = document.getElementById(currentPage + "Link");

  // Si existe el enlace correspondiente, agrega la clase "active"
  if (currentLink) {
    currentLink.classList.add("active");
    // Muestra un mensaje para verificar que se agrega la clase "active" correctamente
    console.log(
      "Clase 'active' agregada al enlace correspondiente: " +
        currentPage +
        "Link"
    );
  }

  // Obtiene los enlaces del navbar
  const actividadLink = document.getElementById("actividadLink");
  const crearActividadLink = document.getElementById("crearActividadLink");
  const visualizarActividadesLink = document.getElementById(
    "visualizarActividadesLink"
  );

  // Añade un evento de clic al enlace de actividad para mostrar la subnavbar
  console.log("hago actividadLink");
  actividadLink.addEventListener("click", function (event) {
    console.log("dentro del click de actividadLink");
    event.preventDefault(); // Previene la acción por defecto del enlace
    subnavbar.style.display = "block"; // Muestra la subnavbar
    console.log("subnavbar mostrada");
  });

  // Añade un evento de clic al documento para ocultar la subnavbar
  document.addEventListener("click", function (event) {
    // Si el clic no fue en el enlace de actividad ni en la subnavbar, oculta la subnavbar
    if (
      !actividadLink.contains(event.target) &&
      !subnavbar.contains(event.target)
    ) {
      subnavbar.style.display = "none"; // Oculta la subnavbar
    }
  });

  // Event listeners para los botones del navbar
  crearActividadLink.addEventListener("click", cargarCrearActividad);
  visualizarActividadesLink.addEventListener(
    "click",
    cargarVisualizarActividades
  );

  // Función para cargar el formulario de creación de actividad
  function cargarCrearActividad() {
    // Oculta todos los formularios y muestra el formulario de creación de actividad
    ocultarFormularios();
    document.getElementById("crearForm").style.display = "block";
    // Muestra un mensaje para verificar que se muestra el formulario de creación de actividad
    console.log("Formulario de creación de actividad mostrado");
  }

  // Función para cargar la visualización de actividades
  function cargarVisualizarActividades() {
    // Oculta todos los formularios y muestra la visualización de actividades
    ocultarFormularios();
    const visualizarActividadesDiv = document.getElementById(
      "visualizarActividadesDiv"
    );
    visualizarActividadesDiv.style.display = "block";
    // Muestra un mensaje para verificar que se muestra la visualización de actividades
    console.log("Visualización de actividades mostrada");

    // Verifica que el servidor esté disponible antes de cargar las actividades
    if (navigator.onLine) {
      cargarActividades(visualizarActividadesDiv);
    } else {
      // Muestra un mensaje de error si no hay conexión a internet
      visualizarActividadesDiv.innerHTML =
        "<tr><td colspan='3'>No hay conexión a internet. Intente nuevamente cuando esté conectado.</td></tr>";
      // Muestra un mensaje para verificar que se muestra el mensaje de error
      console.log("Mensaje de error mostrado");
    }
  }

  // Función para ocultar todos los formularios
  function ocultarFormularios() {
    // Obtiene todos los formularios y los oculta
    const formularios = document.querySelectorAll(
      "#crearForm, #editarForm, #visualizarActividadesDiv"
    );
    formularios.forEach((form) => {
      form.style.display = "none";
    });
    // Muestra un mensaje para verificar que se ocultan los formularios
    console.log("Formularios ocultos");
  }

  // Función para cargar las actividades del servidor
  function cargarActividades() {
    // Obtiene el contenedor de las actividades
    const visualizarActividadesDiv = document.getElementById(
      "actividadesTableBody"
    );

    // Limpia el contenido previo
    visualizarActividadesDiv.innerHTML = "";

    // Realiza una solicitud AJAX para obtener las actividades del servidor
    fetch("GestorActividad", {
      method: "POST",
      body: "action=visualizarActividades",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    })
      .then((response) => {
        // Verifica que la respuesta sea exitosa antes de procesarla
        if (!response.ok) {
          throw new Error("Error en la solicitud AJAX");
        }
        return response.json();
      })
      .then((data) => {
        // Crea filas de la tabla con las actividades
        data.forEach((actividad) => {
          const tr = document.createElement("tr");
          const tdId = document.createElement("td");
          const tdTipo = document.createElement("td");
          const tdFoto = document.createElement("td");

          tdId.textContent = actividad.id;
          tdTipo.textContent = actividad.tipoActividad;

          const img = document.createElement("img");
          img.src = actividad.fotoActividad;
          img.alt = actividad.nombreActividad;
          tdFoto.appendChild(img);

          tr.appendChild(tdId);
          tr.appendChild(tdTipo);
          tr.appendChild(tdFoto);

          // Agrega un evento de clic para editar la actividad
          tr.addEventListener("click", function () {
            cargarEditarActividad(actividad.id);
          });

          visualizarActividadesDiv.appendChild(tr);
        });
        // Muestra un mensaje para verificar que se cargan las actividades
        console.log("Actividades cargadas");
      })
      .catch((error) => {
        console.error("Error al cargar las actividades:", error);
        // Muestra un mensaje de error y carga el contenido de error
        visualizarActividadesDiv.innerHTML =
          "<tr><td colspan='3'>Error al cargar las actividades. Intente nuevamente.</td></tr>";
        // Muestra un mensaje para verificar que se muestra el mensaje de error
        console.log("Mensaje de error mostrado");
      });
  }

  // Función para cargar el formulario de edición de actividad
  function cargarEditarActividad(idActividad) {
    // Oculta todos los formularios y muestra el formulario de edición de actividad
    ocultarFormularios();
    document.getElementById("editarForm").style.display = "block";
    // Aquí podrías cargar los datos de la actividad para editar
    // Muestra un mensaje para verificar que se muestra el formulario de edición de actividad
    console.log("Formulario de edición de actividad mostrado");
  }
});
