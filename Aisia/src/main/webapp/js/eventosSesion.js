// Función para comprobar la sesión del usuario al cargar la página
function comprobarSesion() {
  // Realizar una petición fetch para comprobar el estado de la sesión
  fetch("GestionUsuario?action=comprobarLogin")
    .then((response) => {
      // Verificar si la respuesta es exitosa
      if (!response.ok) {
        // Si la respuesta no es exitosa, lanzar un error
        throw new Error("RESPUESTA DEL SERVLET LOGIN.Sin respuesta del servidor.");
      }
      // Parsear la respuesta como JSON y devolverla
      return response.json();
    })
    .then((data) => {
      // Verificar el estado de la sesión en los datos recibidos
      if (data.status === "OK") {
        // Si el usuario está logueado
        var usuario = data.usuario;
        var permiso = data.permiso;
        console.log("Usuario:", usuario);
        console.log("Permiso:", permiso);

        // Mostrar el nombre de usuario y permiso en el HTML
        document.getElementById("nombreUsuario").innerText = usuario;
        document.getElementById("PermisoUsuario").innerText = permiso;
        document.getElementById("userInfoContainer").style.display = "block";
      } else {
        // Si el usuario no está logueado, mostrar mensaje de error en el HTML
        console.log("El usuario no está logueado");
        document.getElementById("errorMessage").innerText =
          "Usuario no está logueado";
      }
    })
    .catch((error) => {
      // Manejar cualquier error ocurrido durante la petición
      console.error("Error al comprobar la sesión:", error);
      // Mostrar mensaje de error en el HTML
      document.getElementById("errorMessage").innerText =
        "Error al comprobar la sesión";
    });
}

// Llamar a la función para comprobar la sesión al cargar la página
document.addEventListener("DOMContentLoaded", function () {
  comprobarSesion();
});
