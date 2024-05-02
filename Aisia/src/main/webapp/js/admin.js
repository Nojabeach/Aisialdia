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
  // Realizar una solicitud GET al servlet para obtener las actividades
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => response.json())
    .then((data) => {
      // Crear una tabla para mostrar las actividades
      const table = document.createElement("table");
      table.classList.add("table", "table-bordered", "table-striped");

      // Crear una fila para los encabezados de la tabla
      const headerRow = document.createElement("tr");
      headerRow.innerHTML =
        "<th>ID</th><th>Nombre</th><th>Descripción</th><th>Foto</th><th>Acciones</th>";
      table.appendChild(headerRow);

      // Recorrer las actividades y crear una fila para cada una en la tabla
      data.forEach((actividad) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${actividad.id}</td>
          <td>${actividad.nombre}</td>
          <td>${actividad.descripcion}</td>
          <td><img src="${actividad.foto}" alt="${actividad.nombre}" width="100"></td>
          <td>
            <a href="#" class="btn btn-primary" onclick="editarActividad(${actividad.id})">Editar</a>
            <a href="#" class="btn btn-danger" onclick="eliminarActividad(${actividad.id})">Eliminar</a>
          </td>
        `;
        table.appendChild(row);
      });

      // Agregar la tabla a la sección de actividades
      actividadesSection.appendChild(table);
    })
    .catch((error) => console.error(error));
}

// Función para obtener los eventos y mostrarlos en la sección correspondiente
function obtenerEventos() {
  // Realizar una solicitud GET al servlet para obtener los eventos
  fetch("GestorEventos?action=visualizarEventos")
    .then((response) => response.json())
    .then((data) => {
      // Crear una tabla para mostrar los eventos
      const table = document.createElement("table");
      table.classList.add("table", "table-bordered", "table-striped");

      // Crear una fila para los encabezados de la tabla
      const headerRow = document.createElement("tr");
      headerRow.innerHTML =
        "<th>ID</th><th>Nombre</th><th>Descripción</th><th>Fecha</th><th>Hora</th><th>Lugar</th><th>Acciones</th>";
      table.appendChild(headerRow);

      // Recorrer los eventos y crear una fila para cada uno en la tabla
      data.forEach((evento) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${evento.id}</td>
          <td>${evento.nombre}</td>
          <td>${evento.descripcion}</td>
          <td>${evento.fecha}</td>
          <td>${evento.hora}</td>
          <td>${evento.lugar}</td>
          <td>
            <a href="#" class="btn btn-primary" onclick="editarEvento(${evento.id})">Editar</a>
            <a href="#" class="btn btn-danger" onclick="eliminarEvento(${evento.id})">Eliminar</a>
          </td>
        `;
        table.appendChild(row);
      });

      // Agregar la tabla a la sección de eventos
      eventosSection.appendChild(table);
    })
    .catch((error) => console.error(error));
}

// Función para obtener los usuarios y mostrarlos en la sección correspondiente
function obtenerUsuarios() {
  // Realizar una solicitud GET al servlet para obtener los usuarios
  fetch("GestorUsuario?action=visualizarUsuarios")
    .then((response) => response.json())
    .then((data) => {
      // Crear una tabla para mostrar los usuarios
      const table = document.createElement("table");
      table.classList.add("table", "table-bordered", "table-striped");

      // Crear una fila para los encabezados de la tabla
      const headerRow = document.createElement("tr");
      headerRow.innerHTML =
        "<th>ID</th><th>Nombre</th><th>Email</th><th>Permiso</th><th>Acciones</th>";
      table.appendChild(headerRow);

      // Recorrer los usuarios y crear una fila para cada uno en la tabla
      data.forEach((usuario) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${usuario.id}</td>
          <td>${usuario.nombre}</td>
          <td>${usuario.email}</td>
          <td>${usuario.permiso}</td>
          <td>
            <a href="#" class="btn btn-primary" onclick="editarUsuario(${usuario.id})">Editar</a>
            <a href="#" class="btn btn-danger" onclick="eliminarUsuario(${usuario.id})">Eliminar</a>
          </td>
        `;
        table.appendChild(row);
      });

      // Agregar la tabla a la sección de usuarios
      usuariosSection.appendChild(table);
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
      if (xhr.status == 201) {
        alert(xhr.responseText);
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
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
      if (xhr.status == 200) {
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
        // Aquí podrías redirigir a otra página o hacer alguna otra acción
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
  const contrasena = document.getElementById("contrasena").value; // Obtener la contraseña

  // Crear un nuevo objeto FormData para enviar los datos al servlet
  const formData = new FormData();
  formData.append("nombre", nombre);
  formData.append("email", email);
  formData.append("contrasena", contrasena); // Agregar la contraseña al formulario

  // Realizar una solicitud POST al servlet para crear el nuevo usuario
  fetch("GestorUsuario?action=registrarUsuario", {
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
