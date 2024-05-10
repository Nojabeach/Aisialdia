document.addEventListener("DOMContentLoaded", function () {
  console.log("Cargando Actividades");
  AD_obtenerActividades();

  console.log("Cargando Eventos Activos");
  AD_obtenerTodosLosEventosActivos();
  console.log("Cargando Eventos Pendientes Aprobar");
  AD_obtenerPendientesAprobar();
  console.log("Cargando Eventos Pendientes Publicar");
  AD_obtenerPendientesPublicar();
  console.log("Cargando Eventos Rechazados");
  AD_obtenerEventosRechazados();

  console.log("Cargando Usuarios");
  AD_obtenerListadoUsuarios();
});

function AD_obtenerActividades() {
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("actividades-tabla");
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando actividades");
    });
}

function AD_obtenerTodosLosEventosActivos() {
  fetch("GestorEvento?action=obtenerTodosLosEventosActivos")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("Publicados-tabla");
      contenedorAPintar.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos
      pintarTabla(data, contenedorAPintar); // Pintar la tabla
      console.log("Pintando eventos activos");
    });
}

function AD_obtenerPendientesAprobar() {
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
function AD_obtenerPendientesPublicar() {
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
function AD_obtenerEventosRechazados() {
  fetch("GestorEvento?action=obtenerEventosRechazados")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("Rechazados-tabla");
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando eventos rechazados");
    });
}

function AD_obtenerListadoUsuarios() {
  fetch("GestorUsuario?action=obtenerUsuarios")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const contenedorAPintar = document.getElementById("usuarios-tabla");
      pintarTablaEyB(data, contenedorAPintar);
      console.log("Pintando usuarios");
    });
}
