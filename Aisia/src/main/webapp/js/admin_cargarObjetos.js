document.addEventListener("DOMContentLoaded", function () {
  console.log("Cargando Admin Actividades");
  AD_obtenerActividades();
  AD_obtenerEventos(); // Eventos Activos
  AD_obtenerPendientesAprobar();
  AD_obtenerPendientesPublicar();
  AD_obtenerUsuarios();

});

function AD_obtenerActividades() {
  fetch("GestorActividad?action=visualizarActividades")
  .then((response) => response.json())
  .then((data) => {
    console.log("Admin Actividades obtenidas:", data);
    const contenedorAPintar = document.getElementById("actividades-tabla");
    actividad_pintarTablaEditarYBorrar(data, contenedorAPintar);
    console.log("Pintando actividades");
  })
  .catch((error) => {
    console.error("Error al obtener actividades:", error);
  });

} 

function AD_obtenerEventos() {
  fetch("GestorEvento?action=obtenerTodosLosEventosActivos")
  .then((response) => response.json())
  .then((data) => {
    console.log ("Admin Eventos obtenidos:",data);
    const contenedorAPintar = document.getElementById("Publicados-tabla");
    contenedorAPintar.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos
    evento_pintarTablaEditarYBorrar(data, contenedorAPintar); // Pintar la tabla
    console.log("Admin Pintando eventos activos");
  });
}

function AD_obtenerPendientesAprobar() {
  fetch("GestorEvento?action=obtenerEventosPendientesAprobacion")
    .then((response) => response.json())
    .then((data) => {
      console.log("Eventos pendientes de aprobar obtenidos:" + data)
      const contenedorAPintar = document.getElementById(
        "PendientesAprobar-tabla"
      );
      pintarTablaAprobarRechazar(data, contenedorAPintar);
      console.log("Pintando eventos pendientes de aprobar");
    });
}
function AD_obtenerPendientesPublicar() {
  fetch("GestorEvento?action=obtenerEventosPendientesPublicacion")
    .then((response) => response.json())
    .then((data) => {
      console.log("Eventos pendientes de publicar obtenidos:" + data)
      const contenedorAPintar = document.getElementById(
        "PendientesPublicar-tabla"
      );
      pintarTablaPublicar(data, contenedorAPintar);
      console.log("Pintando eventos pendientes de publicar");
    });
}
function AD_obtenerUsuarios() {
  fetch("GestorUsuario?action=obtenerUsuarios")
  .then((response) => response.json())
  .then((data) => {
    console.log("Admin Usuarios obtenidos:", data);
    const contenedorAPintar = document.getElementById("usuarios-tabla");
    usuario_pintarTablaEditarYBorrar(data, contenedorAPintar);
    console.log("Pintando usuarios");
  })
  .catch((error) => {
    console.error("Error al obtener usuarios:", error);
  });
}

    