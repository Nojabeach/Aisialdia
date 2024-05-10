document.addEventListener("DOMContentLoaded", function () {
  console.log("Cargando Actividades");
  obtenerActividades();

  console.log("Cargando Eventos Activos");
  obtenerTodosLosEventosActivos();
  console.log("Cargando Eventos Pendientes Aprobar");
  obtenerPendientesAprobar();
  console.log("Cargando Eventos Pendientes Publicar");
  obtenerPendientesPublicar();

  console.log("Cargando Usuarios");
  obtenerListadoUsuarios();
});

function obtenerActividades() {
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("actividades-tabla");
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando actividades");
    });
}

function obtenerPendientesAprobar() {
  fetch("GestorEvento?action=obtenerEventosPendientesAprobacion")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById(
        "PendientesAprobar-tabla"
      );
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando eventos pendientes de aprobar");
    });
}
function obtenerPendientesPublicar() {
  fetch("GestorEvento?action=obtenerEventosPendientesPublicacion")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById(
        "PendientesPublicar-tabla"
      );
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando eventos pendientes de publicar");
    });
}
function obtenerListadoUsuarios() {
  fetch("GestorUsuario?action=obtenerUsuarios")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("usuarios-tabla");
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando usuarios");
    });
}
