// Resumen del contenido:
// - Función que se ejecuta cuando el documento está listo: cargarPerfil
// - Evento al hacer clic en el botón de editar: editarPerfil
// - Evento al hacer clic en el botón de guardar: guardarPerfil
// - Evento de submit del formulario: guardarPerfil
// - Función para cargar el perfil del usuario
// - Función para guardar el perfil del usuario

$(document).ready(function () {
  // Cargar el perfil del usuario al cargar el documento
  cargarPerfil();

  // Evento al hacer clic en el botón de editar
  $("#editar-btn").click(function () {
    // Habilitar la edición de los inputs
    $("input, textarea").removeAttr("readonly");
    // Mostrar el botón de guardar y ocultar el de editar
    $("#guardar-btn").show();
    $(this).hide();
  });

  // Evento al hacer clic en el botón de guardar
  $("#guardar-btn").click(function () {
    // Enviar el formulario
    $("#perfil-form").submit();
  });

  // Evento de submit del formulario
  $("#perfil-form").submit(function (e) {
    // Evitar el comportamiento por defecto del formulario
    e.preventDefault();
    // Guardar el perfil del usuario
    guardarPerfil();
  });
});

// Función para cargar el perfil del usuario
function cargarPerfil() {
  // Obtener el ID del usuario desde el servlet TrabajoSesion
  $.ajax({
    url: "TrabajoSesion",
    type: "GET",
    success: function (idUsuario) {
      $.ajax({
        url: "GestorUsuario?action=verUsuario&idUsuario=" + idUsuario,
        type: "GET",
        dataType: "json",
        success: function (data) {
          $("#nombre").text(data.nombre);
          $("#email").val(data.email);
          $("#contrasena").val(data.contrasena);
          $("#fechaNacimiento").val(data.fechaNacimiento);
          $("#recibeNotificaciones").prop("checked", data.recibeNotificaciones);
          updateInterestsList(data.intereses);
        },
        error: function (xhr, status, error) {
          console.log("Error al cargar el perfil: " + error);
        },
      });
    },
    error: function (xhr, status, error) {
      console.log("Error al obtener el ID del usuario: " + error);
    },
  });
}

// Función para guardar el perfil del usuario
function guardarPerfil() {
  // Obtener los datos del perfil
  var data = {
    idUsuario: obtenerIdUsuario(), // Obtener el ID de usuario
    nombre: $("#nombre").text(),
    email: $("#email").val(),
    contrasena: $("#contrasena").val(),
    fechaNacimiento: $("#fechaNacimiento").val(),
    recibeNotificaciones: $("#recibeNotificaciones").prop("checked"),
    intereses: $("#intereses").val(),
  };

  // Enviar los datos del perfil al servidor para ser actualizados
  $.ajax({
    url: "GestorUsuario?action=editarUsuario",
    type: "POST",
    dataType: "json",
    data: data,
    success: function (response) {
      // Mostrar mensaje de éxito en consola
      console.log("Perfil actualizado correctamente");
      // Deshabilitar la edición de los inputs
      $("input, textarea").attr("readonly", true);
      // Ocultar el botón de guardar y mostrar el de editar
      $("#guardar-btn").hide();
      $("#editar-btn").show();
    },
    error: function (xhr, status, error) {
      // Mostrar error en consola si no se puede guardar el perfil
      console.log("Error al guardar el perfil: " + error);
    },
  });
}
// Función para actualizar la lista de intereses de forma dinámica
function updateInterestsList(intereses) {
  // Vacía la lista de elementos existente
  $("#intereses-list").empty();

  // Si hay intereses y la lista tiene elementos
  if (interests && interests.length > 0) {
    // Recorre el array de intereses y crea elementos de lista
    for (var i = 0; i < interests.length; i++) {
      var interestItem = "<li>" + interests[i] + "</li>"; // Elemento de lista con el interés
      $("#intereses-list").append(interestItem); // Añade el elemento de lista al contenedor
    }
  } else {
    // Si no hay intereses, muestra un mensaje
    $("#intereses-list").append("<li>No hay intereses agregados.</li>");
  }
}
