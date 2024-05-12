document.addEventListener("DOMContentLoaded", function () {
  console.log("Rellenando favoritos del perfil");

  // Rellenar datos de usuario
  rellenarFormularioPerfil();
  perfil_obtenerFavoritos();

  // Agregar evento al botón de guardar
  let guardarBtn = document.getElementById("guardar-btn");
  if (guardarBtn) {
    guardarBtn.addEventListener("click", function (event) {
      event.preventDefault(); // Evitar que el formulario se envíe automáticamente
      if (validarFormulario("perfil-form")) {
        // Si el formulario es válido, enviarlo
        let form = document.getElementById("perfil-form");
        form.submit();
        // Llamada a servlet para guardar los datos del usuario
        fetch("GestorUsuario?action=editarUsuario", {
          method: "POST",
          body: new FormData(form),
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            if (data.result === "OK") {
              // Si se guardaron los datos correctamente, mostrar mensaje de éxito
              mostrarMensajeExito("Datos guardados correctamente.");
            } else {
              // Si no se guardaron los datos correctamente, mostrar mensaje de error
              mostrarMensajeError(
                "Error al guardar los datos. Inténtelo de nuevo."
              );
            }
          });
      } else {
        console.log("El formulario contiene campos vacíos o incorrectos.");
      }
    });
  }
});

function rellenarFormularioPerfil(idUsuario) {
  console.log("Dentro de rellenarFormularioPerfil");
  infoUsuario();
  // Llamada a servlet para obtener los datos del usuario y rellenar formularios
  let servlet = "GestorUsuario";
  let action = "obtenerInfoUsuario";
  let op = "idUsuario";
  let formularioId = "perfil-form";
  let metodo = "GET";
  console.log(servlet, action, op, formularioId);
  cargarFormularioDesdeServlet(servlet, action, op, formularioId, metodo);
}

function perfil_obtenerFavoritos() {
  fetch("GestorFavorito?action=obtenerEventosFavoritos")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("favoritos-tabla");
      pintarTablaSoloBorrar_Favorito(data, contenedorAPintar);
      console.log("Pintando favoritos");
    });
}
function infoUsuario() {
  // carga la info del usuario en el formulario
  fetch("GestorUsuario?action=checkLogin")
    .then((response) => response.json())
    .then((data) => {
      // Mostrar la información del usuario en el div
      document.getElementById("nombreUsuarioPerfil").innerText =
        data.nombreUsuario;
    })
    .catch((error) => console.error("Error al cargar el usuario:", error));
}
