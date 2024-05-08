document.addEventListener("DOMContentLoaded", function () {
  
  // Mostrar el nombre de usuario logueado
  const nombreUsuarioElement = document.getElementById("nombreUsuario");
  const permisoUsuarioElement = document.getElementById("PermisoUsuario");
  const userInfoContainer = document.getElementById("userInfoContainer");
  const botonCerrarSesion = document.getElementById("botonCerrarSesion");

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

  // Comprobar el estado de inicio de sesión y obtener información del usuario
  fetch("GestorUsuario?action=comprobarLogin")
  alert("hola")
    .then((response) => response.json())
    .then((data) => {
      if (data.status === "OK") {
        nombreUsuarioElement.textContent = data.nombreUsuario;
        permisoUsuarioElement.textContent = `Permiso: ${data.permiso}`;
        userInfoContainer.style.display = "block";
      } else {
        userInfoContainer.style.display = "none";
      }
    })
    .catch((error) => console.error("Error al comprobar el inicio de sesión: ", error));

});
