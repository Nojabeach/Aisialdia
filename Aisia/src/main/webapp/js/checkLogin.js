document.addEventListener("DOMContentLoaded", function () {
  // Función para cargar la información del usuario
  function cargarUsuario() {
    // Realizar una solicitud al servidor para obtener la información del usuario
    fetch("GestorUsuario?action=checkLogin")
      .then((response) => response.json())
      .then((data) => {
        // Verificar si el usuario está logeado
        if (data.nombreUsuario && data.permiso) {
          // Mostrar la información del usuario en el div
          document.getElementById("nombreUsuario").innerText = data.nombreUsuario;
          // Mostrar el botón de cerrar sesión
          document.getElementById("botonCerrarSesion").style.display = "block";
        } else {
          // Si no hay sesión activa, redirigir al usuario a la página de inicio de sesión
          window.location.href = "index.html";
        }
      })
      .catch((error) => console.error("Error al cargar el usuario:", error));
  }

  // Llamar a la función para cargar la información del usuario al cargar la página
  cargarUsuario();

  // Agregar un event listener al botón de cerrar sesión
  document
    .getElementById("botonCerrarSesion")
    .addEventListener("click", function () {
      // Realizar una solicitud al servidor para cerrar sesión
      fetch("GestorUsuario?action=cerrarSesion")
        .then((response) => {
          // Redirigir al usuario a la página de inicio de sesión
          window.location.href = "index.html";
        })
        .catch((error) => console.error("Error al cerrar sesión:", error));
    });
});
