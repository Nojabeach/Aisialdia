

document.addEventListener("DOMContentLoaded", function () {
  console.log ("Rellenando favoritos del perfil");

// Variable global para almacenar el idUsuario
var idUsuarioGlobal;

// En el login, después de obtener el idUsuario


  perfil_obtenerFavoritos();
  rellenarFormularioPerfil();

// Agregar evento al botón de guardar
let guardarBtn = document.getElementById("guardar-btn");
if (guardarBtn) {
    guardarBtn.addEventListener("click", function(event) {
        event.preventDefault(); // Evitar que el formulario se envíe automáticamente
        if (validarFormulario("perfil-form")) {
            // Si el formulario es válido, enviarlo
            let form = document.getElementById("perfil-form");
            form.submit();
        } else {
            console.log("El formulario contiene campos vacíos o incorrectos.");
        }
    });
}

});

function rellenarFormularioPerfil() {
  console.log("Dentro de rellenarFormularioPerfil");

  // Asignar el idUsuario al nombre de usuario en el perfil
  let nombreUsuarioPerfil = document.getElementById("nombreUsuarioPerfil");
  if (nombreUsuarioPerfil) {
    nombreUsuarioPerfil.textContent = idUsuarioGlobal;
  }

  // Llamada a servlet para obtener los datos del usuario y rellenar formularios
  let servlet = 'GestorUsuario';
  let action = 'editarUsuario';
  let op = idUsuarioGlobal; // Utilizamos el idUsuarioGlobal
  let formularioId = "perfil-form";
  console.log(servlet, action, op, formularioId);
  cargarFormularioDesdeServlet(servlet, action, op, formularioId);
}



function  perfil_obtenerFavoritos() {
  fetch("GestorFavorito?action=obtenerEventosFavoritos")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("favoritos-tabla");
      pintarTablaSoloBorrar_Favorito(data, contenedorAPintar);
      console.log("Pintando favoritos");
    });
}