document.addEventListener("DOMContentLoaded", function () {
  console.log("Rellenando favoritos del perfil");

  // Rellenar datos de usuario
  infoUsuario(); // Obtener la información del usuario y mostrarla en el div
  perfil_rellenarIntereses();
  rellenarFormularioPerfil();
  perfil_obtenerFavoritos();

  // Botón de guardar
  document.getElementById('guardar-btn').addEventListener('click', function () {
    actualizarUsuario();
  });

  // Botón de cambiar contraseña
  document.getElementById('cambiarContrasena').addEventListener('click', function () {
    mostrarMensaje('Contraseña ha sido guardada correctamente');
  });

});

function actualizarUsuario() {
  let formulario = document.getElementById("perfil-form");
  let formData = new FormData(formulario);
  fetch(formulario.action, {
      method: formulario.method,
      body: formData
  })
  .then(response => {
      if (response.ok) {
          mostrarMensaje('Datos guardados correctamente');
      } 
  })
}

function rellenarFormularioPerfil(idUsuario) {
  console.log("Dentro de rellenarFormularioPerfil");
  infoUsuario();
  // Llamada a servlet para obtener los datos del usuario y rellenar formularios
  let servlet = "GestorUsuario";
  let action = "obtenerINFOUsuarioSesion";
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

// Función para mostrar el mensaje
function mostrarMensaje(mensaje) {
  var mensajeContainer = document.getElementById('mensaje-container');
  mensajeContainer.innerText = mensaje;
  mensajeContainer.style.display = 'block';
  setTimeout(function() {
      mensajeContainer.style.display = 'none';
  }, 4000); // Mostrar el mensaje durante 4 segundos
}
