// Función para obtener la lista de usuarios y pintarla en la tabla
function AD_obtenerUsuarios() {
    fetch("GestorUsuario?action=obtenerUsuarios")
     .then((response) => response.json())
     .then((data) => {
        console.log("Admin Usuarios obtenidos:", data);
        if (data && data.length > 0) {
          const contenedorAPintar = document.getElementById("usuarios-tabla");
          usuario_pintarTablaEditarYBorrar(data, contenedorAPintar);
          console.log("Pintando usuarios");
        } else {
          console.log("No se encontraron usuarios");
        }
      })
     .catch((error) => {
        console.error("Error al obtener usuarios:", error);
      });
  }
  
  // Función para crear un usuario
  function adminUS_crearUsuario() {
    console.log("Entrando en adminUS_crearUsuario()");
    const formData = new FormData(document.getElementById("usuariosForm"));
    fetch("GestorUsuario?action=crearUsuario", {
      method: "POST",
      body: formData,
    })
     .then((response) => response.json())
     .then((data) => {
        console.log("Usuario creado exitosamente!");
        actualizarTablaUsuarios(); // Actualizar la tabla de usuarios
      })
     .catch((error) => {
        console.error("Error al crear usuario:", error);
      });
  }
  
  // Función para editar un usuario
  function adminUS_editarUsuario() {
    console.log("Entrando en adminUS_editarUsuario()");
    const formData = new FormData(document.getElementById("EDITusuariosForm"));
    fetch("GestorUsuario?action=editarUsuarioAdmin", {
      method: "POST",
      body: formData,
    })
     .then((response) => response.json())
     .then((data) => {
        console.log("Usuario editado exitosamente!");
        actualizarTablaUsuarios(); // Actualizar la tabla de usuarios
      })
     .catch((error) => {
        console.error("Error al editar usuario:", error);
      });
  }
  
  // Función para actualizar la tabla de usuarios
  function actualizarTablaUsuarios() {
    AD_obtenerUsuarios();
  }
  
// Esperar a que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", function() {
  // Cargar la tabla de usuarios
  AD_obtenerUsuarios();
  
  // Asignar event listener para el botón de crear usuario
  document.getElementById("crear-usuario-button").addEventListener("click", adminUS_crearUsuario);
  
  // Asignar event listener para el botón de editar usuario
  document.getElementById("editar-usuario-button").addEventListener("click", adminUS_editarUsuario);
});