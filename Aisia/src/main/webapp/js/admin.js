// Resumen:
// 1. Obtener referencias a los elementos del DOM
// 2. Obtener actividades, eventos y usuarios y mostrarlos en las secciones correspondientes
// 3. Crear, editar y eliminar actividades
// 4. Crear, editar y eliminar eventos
// 5. Crear, editar y eliminar usuarios
// 6. Publicar, rechazar y aprobar eventos

//**************************************************************************
// OBTENER REFERENCIAS A LOS ELEMENTOS DEL DOM
//**************************************************************************
const actividadesSection = document.getElementById("actividades");
const eventosSection = document.getElementById("eventos");
const usuariosSection = document.getElementById("usuarios");
//**************************************************************************
// OBTENER ACTIVIDADES, EVENTOS Y USUARIOS
//**************************************************************************
// Función para obtener las actividades y mostrarlas en la sección correspondiente
function obtenerActividades() {
  fetch("GestorActividad?action=visualizarActividades") // Realizar una solicitud GET al servlet para obtener las actividades
    .then((response) => response.json())
    .then((data) => {
      const headers = ["ID", "Nombre", "Descripción", "Foto", "Acciones"]; // Encabezados de la tabla
      const table = crearTabla(
        // Crear la tabla con los datos recibidos
        // Se muestra el ID, nombre, descripción, foto y botones para editar y eliminar
        data.map((actividad) => [
          actividad.id,
          actividad.nombre,
          actividad.descripcion,
          `<img src="${actividad.foto}" alt="${actividad.nombre}" width="100">`,
          `
              <a href="#" class="btn btn-primary" onclick="editarActividad(${actividad.id})">Editar</a>
              <a href="#" class="btn btn-danger" onclick="eliminarActividad(${actividad.id})">Eliminar</a>
            `,
        ]),
        headers
      );
      actividadesSection.appendChild(table); // Agregar la tabla a la sección de actividades
    })
    .catch((error) => console.error(error));
}
// Función para obtener los eventos y mostrarlos en la sección correspondiente
function obtenerEventos() {
  fetch("GestorEventos?action=visualizarEventos") // Realizar una solicitud GET al servlet para obtener los eventos
    .then((response) => response.json())
    .then((data) => {
      const headers = [
        "ID",
        "Nombre",
        "Descripción",
        "Fecha",
        "Hora",
        "Lugar",
        "Acciones",
      ]; // Encabezados de la tabla
      const table = crearTabla(
        // Crear la tabla con los datos recibidos
        // Se muestra el ID, nombre, descripción, fecha, hora, lugar y botones para editar y eliminar
        data.map((evento) => [
          evento.id,
          evento.nombre,
          evento.descripcion,
          evento.fecha,
          evento.hora,
          evento.lugar,
          `
              <a href="#" class="btn btn-primary" onclick="editarEvento(${evento.id})">Editar</a>
              <a href="#" class="btn btn-danger" onclick="eliminarEvento(${evento.id})">Eliminar</a>
            `,
        ]),
        headers
      );
      eventosSection.appendChild(table); // Agregar la tabla a la sección de eventos
    })
    .catch((error) => console.error(error));
}

// Función para obtener los usuarios y mostrarlos en la sección correspondiente
function obtenerUsuarios() {
  fetch("GestorUsuario?action=visualizarUsuarios") // Realizar una solicitud GET al servlet para obtener los usuarios
    .then((response) => response.json())
    .then((data) => {
      const headers = ["ID", "Nombre", "Email", "Permiso", "Acciones"]; // Encabezados de la tabla
      const table = crearTabla(
        // Crear la tabla con los datos recibidos
        // Se muestra el ID, nombre, email, permiso y botones para editar y eliminar
        data.map((usuario) => [
          usuario.id,
          usuario.nombre,
          usuario.email,
          usuario.permiso,
          `
              <a href="#" class="btn btn-primary" onclick="editarUsuario(${usuario.id})">Editar</a>
              <a href="#" class="btn btn-danger" onclick="eliminarUsuario(${usuario.id})">Eliminar</a>
            `,
        ]),
        headers
      );
      usuariosSection.appendChild(table); // Agregar la tabla a la sección de usuarios
    })
    .catch((error) => console.error(error));
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

  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorActividad", true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      // La solicitud ha sido completada
      if (xhr.status == 201) {
        // La solicitud ha sido completada con éxito
        alert(xhr.responseText);
        // Llamar a la función obtenerActividades() para actualizar la lista
        obtenerActividades();
      } else {
        alert("Error al crear la actividad. Intente de nuevo.");
      }
    }
  };
  xhr.send(formData);
}

// Función para editar una actividad
function editarActividad(idActividad) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorActividad", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      // La solicitud ha sido completada
      if (xhr.status == 200) {
        // La solicitud ha sido completada con éxito
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al editar la actividad. Intente de nuevo.");
      }
    }
  };
  xhr.send(
    "action=editarActividad&idActividad=" + encodeURIComponent(idActividad)
  );
}

// Función para eliminar una actividad
function eliminarActividad(idActividad) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorActividad", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al eliminar la actividad. Intente de nuevo.");
      }
    }
  };
  xhr.send(
    "action=eliminarActividad&idActividad=" + encodeURIComponent(idActividad)
  );
}

// Función para crear un nuevo evento
function crearEvento() {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;
  var idUsuarioCreador = document.getElementById("idUsuarioCreador").value;
  var ubicacion = document.getElementById("ubicacion").value;

  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 201) {
        alert(xhr.responseText);
        // Llamar a la función obtenerEventos() para actualizar la lista
        obtenerEventos();
      } else {
        alert("Error al crear el evento. Intente de nuevo.");
      }
    }
  };
  xhr.send(
    "action=crearEvento&nombre=" +
      encodeURIComponent(nombre) +
      "&detalles=" +
      encodeURIComponent(detalles) +
      "&idUsuarioCreador=" +
      encodeURIComponent(idUsuarioCreador) +
      "&ubicacion=" +
      encodeURIComponent(ubicacion)
  );
}

// Función para editar un evento existente
function editarEvento(idEvento) {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;

  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al editar el evento. Intente de nuevo.");
      }
    }
  };
  xhr.send(
    "action=editarEvento&idEvento=" +
      encodeURIComponent(idEvento) +
      "&nombre=" +
      encodeURIComponent(nombre) +
      "&detalles=" +
      encodeURIComponent(detalles)
  );
}

// Función para eliminar un evento existente
function eliminarEvento(idEvento) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al eliminar el evento. Intente de nuevo.");
      }
    }
  };
  xhr.send("action=eliminarEvento&idEvento=" + encodeURIComponent(idEvento));
}
//**************************************************************************
// 5. CREAR, EDITAR Y ELIMINAR USUARIOS
//**************************************************************************

// Función para crear un nuevo usuario
function crearUsuario() {
  // Obtener los valores de los campos de entrada
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const contrasena = document.getElementById("contrasena").value;

  // Crear un nuevo objeto FormData para enviar los datos al servlet
  const formData = new FormData();
  formData.append("nombre", nombre);
  formData.append("email", email);
  formData.append("contrasena", contrasena);

  // Realizar una solicitud POST al servlet para crear el nuevo usuario
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "GestorUsuario?action=registrarUsuario", true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 201) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
        obtenerUsuarios(); // Actualizar la lista de usuarios
      } else {
        alert("Error al crear el usuario. Intente de nuevo.");
      }
    }
  };
  xhr.send(formData);
}
// Función para editar un usuario existente
function editarUsuario(id) {
  // Obtener los valores de los campos de entrada
  const nombre = prompt("Introduzca el nuevo nombre del usuario:");
  const email = prompt("Introduzca el nuevo email del usuario:");

  // Crear un nuevo objeto FormData para enviar los datos al servlet
  const formData = new FormData();
  formData.append("idUsuario", id);
  formData.append("nombre", nombre);
  formData.append("email", email);

  // Realizar una solicitud POST al servlet para editar el usuario
  fetch("GestorUsuario?action=editarUsuario", {
    method: "POST",
    body: formData,
  })
    .then((response) => response.text())
    .then((data) => {
      // Mostrar el mensaje de éxito y actualizar la lista de usuarios
      alert(data);
      obtenerUsuarios();
    })
    .catch((error) => console.error(error));
}

// Función para eliminar un usuario existente
function eliminarUsuario(id) {
  // Realizar una solicitud GET al servlet para eliminar el usuario
  fetch(`GestorUsuario?action=eliminarUsuario&idUsuario=${id}`)
    .then((response) => response.text())
    .then((data) => {
      // Mostrar el mensaje de éxito y actualizar la lista de usuarios
      alert(data);
      obtenerUsuarios();
    })
    .catch((error) => console.error(error));
}

// Llamar a las funciones para obtener y mostrar actividades, eventos y usuarios
obtenerActividades();
obtenerEventos();
obtenerUsuarios();

//**************************************************************************
// PUBLICAR, RECHAZAR Y APROBAR EVENTOS
//**************************************************************************

// Función para publicar un evento
function publicarEvento(idEvento) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al publicar el evento. Intente de nuevo.");
      }
    }
  };
  xhr.send("action=publicarEvento&idEvento=" + encodeURIComponent(idEvento));
}

// Función para rechazar un evento
function rechazarEvento(idEvento) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al rechazar el evento. Intente de nuevo.");
      }
    }
  };
  xhr.send("action=rechazarEvento&idEvento=" + encodeURIComponent(idEvento));
}

// Función para aprobar la publicación de un evento
function aprobarPublicacionEvento(idEvento) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "gestorEvento", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
      } else {
        alert("Error al aprobar la publicación del evento. Intente de nuevo.");
      }
    }
  };
  xhr.send(
    "action=aprobarPublicacionEvento&idEvento=" + encodeURIComponent(idEvento)
  );
}

// Función para crear una tabla con los datos proporcionados
function crearTabla(data, headers) {
  const table = document.createElement("table");
  table.classList.add("table", "table-bordered", "table-striped");

  const headerRow = document.createElement("tr");
  headers.forEach((header) => {
    const th = document.createElement("th");
    th.textContent = header;
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

  data.forEach((item) => {
    const row = document.createElement("tr");
    headers.forEach((header, index) => {
      const td = document.createElement("td");
      td.textContent = item[index];
      row.appendChild(td);
    });
    table.appendChild(row);
  });

  return table;
}
