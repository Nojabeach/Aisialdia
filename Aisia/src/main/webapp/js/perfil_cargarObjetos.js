document.addEventListener("DOMContentLoaded", function () {
  console.log("Rellenando favoritos del perfil");

  // Rellenar datos de usuario
  perfil_rellenarIntereses();
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


function perfil_rellenarIntereses(){
  // Obtener el elemento select donde se cargarán los intereses
  const interesesSelect = document.getElementById("intereses");

  // Array para almacenar los intereses seleccionados
  let interesesSeleccionados = [];

  // Realizar una solicitud Fetch al servidor para obtener los datos de intereses
  fetch("GestorIntereses?accion=listar")
    .then((response) => response.json())
    .then((data) => {
      // Iterar sobre los datos y agregar cada nombre de interés como una opción en el select
      data.forEach((nombreInteres) => {
        const option = new Option(nombreInteres, nombreInteres);
        interesesSelect.add(option);
      });
    })
    .catch((error) => console.error(error)); // Capturar y mostrar cualquier error que ocurra durante la solicitud

  }