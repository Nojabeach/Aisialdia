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
  fetch("GestorActividad?action=visualizarActividades")
    .then((response) => response.json())
    .then((data) => {
      const headers = ["ID", "Nombre", "Descripción", "Foto", "Acciones"];
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
    .catch((error) => console.error(error));
}

// Función para obtener los eventos y mostrarlos en la sección correspondiente
function obtenerEventos() {
  fetch("GestorEventos?action=visualizarEventos")
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
      ];
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
    .catch((error) => console.error(error));
}

// Función para obtener los usuarios y mostrarlos en la sección correspondiente
function obtenerUsuarios() {
  fetch("GestorUsuario?action=visualizarUsuarios")
    .then((response) => response.json())
    .then((data) => {
      const headers = ["ID", "Nombre", "Email", "Permiso", "Acciones"];
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
      alert(data);
      obtenerActividades();
    })
    .catch((error) => console.error(error));
}

// Función para editar una actividad
function editarActividad(idActividad) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
}

// Función para eliminar una actividad
function eliminarActividad(idActividad) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
}

// Función para crear un nuevo evento
function crearEvento() {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;
  var idUsuarioCreador = document.getElementById("idUsuarioCreador").value;
  var ubicacion = document.getElementById("ubicacion").value;

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
    )}&ubicacion=${encodeURIComponent(ubicacion)}`,
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al crear el evento. Intente de nuevo.");
    })
    .then((data) => {
      alert(data);
      obtenerEventos();
    })
    .catch((error) => console.error(error));
}

// Función para editar un evento existente
function editarEvento(idEvento) {
  var nombre = document.getElementById("nombre").value;
  var detalles = document.getElementById("detalles").value;

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
      alert(data);
    })
    .catch((error) => console.error(error));
}

// Función para eliminar un evento existente
function eliminarEvento(idEvento) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
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
      alert(data);
      obtenerUsuarios();
    })
    .catch((error) => console.error(error));
}

// Función para editar un usuario existente
function editarUsuario(id) {
  const nombre = prompt("Introduzca el nuevo nombre del usuario:");
  const email = prompt("Introduzca el nuevo email del usuario:");

  const formData = new FormData();
  formData.append("idUsuario", id);
  formData.append("nombre", nombre);
  formData.append("email", email);

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
      alert(data);
      obtenerUsuarios();
    })
    .catch((error) => console.error(error));
}

// Función para eliminar un usuario existente
function eliminarUsuario(id) {
  fetch(`GestorUsuario?action=eliminarUsuario&idUsuario=${id}`)
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Error al eliminar el usuario. Intente de nuevo.");
    })
    .then((data) => {
      alert(data);
      obtenerUsuarios();
    })
    .catch((error) => console.error(error));
}

//**************************************************************************
// PUBLICAR, RECHAZAR Y APROBAR EVENTOS
//**************************************************************************
// Función para publicar un evento
function publicarEvento(idEvento) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
}

// Función para rechazar un evento
function rechazarEvento(idEvento) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
}

// Función para aprobar la publicación de un evento
function aprobarPublicacionEvento(idEvento) {
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
      alert(data);
    })
    .catch((error) => console.error(error));
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

// Llamar a las funciones para obtener y mostrar actividades, eventos y usuarios
obtenerActividades();
obtenerEventos();
obtenerUsuarios();
