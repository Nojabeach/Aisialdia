document.addEventListener("DOMContentLoaded", function () {
    console.log ("rellenando favoritos del perfil");
    obtenerFavoritos();

     rellenarFormularioPerfil();
}  );

function rellenarFormularioPerfil() {
    console.log("dentro de rellenarFormularioPerfil");
    
  let servlet = 'GestorUsuario';
  let action = 'editarUsuario';
  let op = getParameterByName("idUsuario");
  let formularioId = 'perfilusuario.html'; 
  console.log(servlet, action, op, formularioId);
  alert(servlet, action, op, formularioId);
  llamada(servlet, action, op, formularioId);
}