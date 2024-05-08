//Autor: Maitane Ibañez Irazabal
//Fecha: 02/05/2024
// Versión: 1.0
// Descripción: Archivo JS para la administración de actividades, eventos y usuarios
// Resumen:
// 1. Obtener referencias a los elementos del DOM
// 2. Obtener actividades, eventos y usuarios y mostrarlos en las secciones correspondientes
// 3. Crear, editar y eliminar actividades
// 4. Crear, editar y eliminar eventos
// 5. Crear, editar y eliminar usuarios
// 6. Publicar, rechazar y aprobar eventos
// 7. Crear una tabla con los datos proporcionados
// 8. Agregar evento al botón de cerrar sesión

//**************************************************************************
// OBTENER REFERENCIAS A LOS ELEMENTOS DEL DOM
//**************************************************************************
const actividadesSection = document.getElementById("actividades"); // Se obtiene la referencia a la sección de actividades en el DOM
const eventosSection = document.getElementById("eventos"); // Se obtiene la referencia a la sección de eventos en el DOM
const usuariosSection = document.getElementById("usuarios"); // Se obtiene la referencia a la sección de usuarios en el DOM

//**************************************************************************
// OBTENER ACTIVIDADES, EVENTOS Y USUARIOS
//**************************************************************************
// Función para obtener las actividades y mostrarlas en la sección correspondiente
function obtenerActividades() {
  // Se realiza una petición fetch para obtener las actividades desde el servidor
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => response.json()) // Se convierte la respuesta a JSON
    .then((data) => {
      // Se maneja la respuesta de la petición
      const headers = ["ID", "Nombre", "Descripción", "Foto", "Acciones"]; // Encabezados de la tabla de actividades
      // Se crea una tabla con los datos obtenidos y se agrega a la sección de actividades en el DOM
      const table = crearTabla(
        data.map((actividad) => [
          actividad.id,
          actividad.nombre,
          actividad.descripcion,
          `<img src="${actividad.foto}" alt="${actividad.nombre}" width="100">`,
          `<button class="btn btn-editar" onclick="editar('actividad', ${actividad.id})">Editar</button>
           <button class="btn btn-eliminar" onclick="eliminar('actividad', ${actividad.id})">Eliminar</button>`,
        ]),
        headers
      );
      actividadesSection.appendChild(table);
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para obtener los eventos y mostrarlos en la sección correspondiente
function obtenerEventos() {
  // Se realiza una petición fetch para obtener los eventos desde el servidor
  fetch("GestorEventos?action=visualizarEventos")
    .then((response) => response.json()) // Se convierte la respuesta a JSON
    .then((data) => {
      // Se maneja la respuesta de la petición
      const headers = [
        "ID",
        "Nombre",
        "Descripción",
        "Fecha",
        "Hora",
        "Lugar",
        "Acciones",
      ]; // Encabezados de la tabla de eventos
      // Se crea una tabla con los datos obtenidos y se agrega a la sección de eventos en el DOM
      const table = crearTabla(
        data.map((evento) => [
          evento.id,
          evento.nombre,
          evento.descripcion,
          evento.fecha,
          evento.hora,
          evento.lugar,
          `<button class="btn btn-editar" onclick="editarEvento(${evento.id})">Editar</button>
           <button class="btn btn-eliminar" onclick="eliminarEvento(${evento.id})">Eliminar</button>
           <button class="btn btn-publicar" onclick="publicarEvento(${evento.id})">Publicar</button>
           <button class="btn btn-rechazar" onclick="rechazarEvento(${evento.id})">Rechazar</button>
           <button class="btn btn-publicar" onclick="aprobarPublicacionEvento(${evento.id})">Aprobar Publicación</button>`,
        ]),
        headers
      );
      eventosSection.appendChild(table);
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para obtener los usuarios y mostrarlos en la sección correspondiente
function obtenerUsuarios() {
  // Se realiza una petición fetch para obtener los usuarios desde el servidor
  fetch("GestorUsuario?action=visualizarUsuarios")
    .then((response) => response.json()) // Se convierte la respuesta a JSON
    .then((data) => {
      // Se maneja la respuesta de la petición
      const headers = ["ID", "Nombre", "Email", "Permiso", "Acciones"]; // Encabezados de la tabla de usuarios
      // Se crea una tabla con los datos obtenidos y se agrega a la sección de usuarios en el DOM
      const table = crearTabla(
        data.map((usuario) => [
          usuario.id,
          usuario.nombre,
          usuario.email,
          usuario.permiso,
          `<button class="btn btn-editar" onclick="editar('usuario', ${usuario.id})">Editar</button>
           <button class="btn btn-eliminar" onclick="eliminar('usuario', ${usuario.id})">Eliminar</button>`,
        ]),
        headers
      );
      usuariosSection.appendChild(table);
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

//**************************************************************************
// CREAR, EDITAR Y ELIMINAR ACTIVIDADES, EVENTOS Y USUARIOS
//**************************************************************************
// Función para crear una actividad
function crearActividad() {
  var formData = new FormData();
  formData.append("action", "crearActividad");
  formData.append(
    "tipoActividad",
    document.getElementById("tipoActividad").value
  );
  formData.append(
    "fotoActividad",
    document.getElementById("fotoActividad").files[0]
  );

  // Se realiza una petición fetch para enviar los datos de la nueva actividad al servidor
  fetch("gestorActividad", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al crear la actividad. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
      obtenerActividades(); // Se vuelven a obtener y mostrar las actividades actualizadas
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para editar una actividad
function editarActividad(idActividad) {
  // Se realiza una petición fetch para enviar la solicitud de edición de la actividad al servidor
  fetch("gestorActividad", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=editarActividad&idActividad=${encodeURIComponent(
      idActividad
    )}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al editar la actividad. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra

      //un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para eliminar una actividad
function eliminarActividad(idActividad) {
  // Se realiza una petición fetch para enviar la solicitud de eliminación de la actividad al servidor
  fetch("gestorActividad", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=eliminarActividad&idActividad=${encodeURIComponent(
      idActividad
    )}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al eliminar la actividad. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para crear un nuevo evento
function crearEvento() {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;
  var idUsuarioCreador = document.getElementById("idUsuarioCreador").value;
  var ubicacion = document.getElementById("ubicacion").value;
  var actividades = [];
  var actividadChecks = document.getElementsByName("actividad");
  for (var i = 0; i < actividadChecks.length; i++) {
    if (actividadChecks[i].checked) {
      actividades.push(actividadChecks[i].value);
    }
  }

  // Se realiza una petición fetch para enviar los datos del nuevo evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=crearEvento&nombre=${encodeURIComponent(
      nombre
    )}&detalles=${encodeURIComponent(
      detalles
    )}&idUsuarioCreador=${encodeURIComponent(
      idUsuarioCreador
    )}&ubicacion=${encodeURIComponent(ubicacion)}&actividades=${encodeURIComponent(actividades)}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al crear el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
      obtenerEventos(); // Se vuelven a obtener y mostrar los eventos actualizados
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para editar un evento existente
function editarEvento(idEvento) {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;

  // Se realiza una petición fetch para enviar la solicitud de edición del evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=editarEvento&idEvento=${encodeURIComponent(
      idEvento
    )}&nombre=${encodeURIComponent(nombre)}&detalles=${encodeURIComponent(
      detalles
    )}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al editar el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para eliminar un evento existente
function eliminarEvento(idEvento) {
  // Se realiza una petición fetch para enviar la solicitud de eliminación del evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=eliminarEvento&idEvento=${encodeURIComponent(idEvento)}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al eliminar el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para crear un nuevo usuario
function crearUsuario() {
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const contrasena = document.getElementById("contrasena").value;

  const formData = new FormData();
  formData.append("nombre", nombre);
  formData.append("email", email);
  formData.append("contrasena", contrasena);

  // Se realiza una petición fetch para enviar los datos del nuevo usuario al servidor
  fetch("GestorUsuario?action=registrarUsuario", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al crear el usuario. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
      obtenerUsuarios(); // Se vuelven a obtener y mostrar los usuarios actualizados
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para editar un usuario existente
function editarUsuario(id) {
  const nombre = prompt("Introduzca el nuevo nombre del usuario:");
  const email = prompt("Introduzca el nuevo email del usuario:");

  const formData = new FormData();
  formData.append("idUsuario", id);
  formData.append("nombre", nombre);
  formData.append("email", email);

  // Se realiza una petición fetch para enviar la solicitud de edición del usuario al servidor
  fetch("GestorUsuario?action=editarUsuario", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al editar el usuario. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
      obtenerUsuarios(); // Se vuelven a obtener y mostrar los usuarios actualizados
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para eliminar un usuario existente
function eliminarUsuario(id) {
  // Se realiza una petición fetch para enviar la solicitud de eliminación del usuario al servidor
  fetch(`GestorUsuario?action=eliminarUsuario&idUsuario=${id}`)
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al eliminar el usuario. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
      obtenerUsuarios(); // Se vuelven a obtener y mostrar los usuarios actualizados
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

//**************************************************************************
// PUBLICAR, RECHAZAR Y APROBAR EVENTOS
//**************************************************************************
// Función para publicar un evento
function publicarEvento(idEvento) {
  // Se realiza una petición fetch para enviar la solicitud de publicación del evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=publicarEvento&idEvento=${encodeURIComponent(idEvento)}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al publicar el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para rechazar un evento
function rechazarEvento(idEvento) {
  // Se realiza una petición fetch para enviar la solicitud de rechazo del evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=rechazarEvento&idEvento=${encodeURIComponent(idEvento)}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al rechazar el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para aprobar la publicación de un evento
function aprobarPublicacionEvento(idEvento) {
  // Se realiza una petición fetch para enviar la solicitud de aprobación de publicación del evento al servidor
  fetch("gestorEvento", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `action=aprobarPublicacionEvento&idEvento=${encodeURIComponent(
      idEvento
    )}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error(
        "Error al aprobar la publicación del evento. Intente de nuevo."
      );
    })
    .then((data) => {
      alert(data); // Se muestra un mensaje de alerta con la respuesta del servidor
    })
    .catch((error) => console.error(error)); // Se maneja cualquier error
}

// Función para crear una tabla con los datos proporcionados
function crearTabla(data, headers) {
  const table = document.createElement("table"); // Se crea un elemento de tabla en el DOM
  table.classList.add("table", "table-bordered", "table-striped"); // Se agregan clases a la tabla para darle formato

  const headerRow = document.createElement("tr"); // Se crea una fila para los encabezados
  headers.forEach((header) => {
    const th = document.createElement("th"); // Se crea una celda de encabezado
    th.textContent = header; // Se establece el texto del encabezado
    headerRow.appendChild(th); // Se agrega el encabezado a la fila
  });
  table.appendChild(headerRow); // Se agrega la fila de encabezados a la tabla

  // Se recorre cada item de los datos para crear filas de la tabla
  data.forEach((item) => {
    const row = document.createElement("tr"); // Se crea una fila para un item
    headers.forEach((header, index) => {
      const td = document.createElement("td"); // Se crea una celda para cada valor del item
      td.textContent = item[index]; // Se establece el texto de la celda
      row.appendChild(td); // Se agrega la celda a la fila
    });
    table.appendChild(row); // Se agrega la fila a la tabla
  });

  return table; // Se retorna la tabla completa
}

// Llamar a las funciones para obtener y mostrar actividades, eventos y usuarios
obtenerActividades();
obtenerEventos();
obtenerUsuarios();




 // Agregar evento al botón de cerrar sesión
 botonCerrarSesion.addEventListener("click", () => {
  // Llamar al servlet para cerrar sesión
  fetch("GestorUsuario?action=cerrarSesion")
    .then((response) => response.text())
    .then(() => {
      // Redirigir a index.html
      window.location.href = "index.html";
    })
    .catch((error) => console.error(error));
});