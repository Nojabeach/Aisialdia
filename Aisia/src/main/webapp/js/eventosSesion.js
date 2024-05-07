document.addEventListener("DOMContentLoaded", function () {
  const eventsContainer = document.getElementById("events-container");

  // Mostrar el nombre de usuario logueado
  const nombreUsuarioElement = document.getElementById("nombreUsuario");
  const userInfoContainer = document.getElementById("userInfoContainer");
  const botonCerrarSesion = document.getElementById("botonCerrarSesion");

  fetch("GestorUsuario?action=obtenerNombreUsuario")
    .then((response) => response.text())
    .then((nombreUsuario) => {
      if (nombreUsuario) {
        nombreUsuarioElement.textContent = nombreUsuario;
        userInfoContainer.style.display = "block";

        // Obtener permiso del usuario
        fetch("GestorUsuario?action=obtenerPermisoUsuario")
          .then((response) => response.text())
          .then((permiso) => {
            const userPermisoElement = document.getElementById("PermisoUsuario");
            userPermisoElement.textContent = `Permiso: ${permiso}`;
          })
          .catch((error) => console.error("Error al obtener permiso: ", error));
      } else {
        userInfoContainer.style.display = "none";
      }
    })
    .catch((error) => console.error("Error al obtener nombre de usuario: ", error));

  // Agregar evento al botón de cerrar sesión
  botonCerrarSesion.addEventListener("click", () => {
    // Llamar al servlet para cerrar sesión
    fetch("GestorUsuario?action=cerrarSesion")
      .then((response) => response.text())
      .then(() => {
        // Redirigir a index.html
        window.location.href = "index.html";
      })
      .catch((error) => console.error(error));
  });
});