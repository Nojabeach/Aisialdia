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