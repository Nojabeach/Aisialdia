function pintarTablaEyB(data, idTabla) {
  let tabla = document.createElement("table");
  // Agregar clases de estilo a la tabla
  tabla.classList.add("tabla");

  // Limpiar la tabla antes de agregar nuevos datos
  idTabla.innerHTML = "";

  // Crear encabezados de tabla solo para las columnas con datos
  let columnasConDatos = Object.keys(data[0]).filter((columna) => {
    return data.some(
      (item) =>
        item[columna] !== 0 &&
        item[columna] !== null &&
        item[columna] !== undefined
    );
  });

  let thead = document.createElement("thead");
  let filaEncabezado = document.createElement("tr");
  columnasConDatos.forEach((columna) => {
    let th = document.createElement("th");
    th.textContent = columna;
    filaEncabezado.appendChild(th);
  });
  // Agregar una columna extra para el botón de borrar
  filaEncabezado.innerHTML += "<th>Acciones</th>";
  thead.appendChild(filaEncabezado);
  tabla.appendChild(thead);

  // Crear cuerpo de tabla
  let tbody = document.createElement("tbody");
  data.forEach((item) => {
    let fila = document.createElement("tr");

    columnasConDatos.forEach((columna) => {
      if (
        item[columna] !== 0 &&
        item[columna] !== null &&
        item[columna] !== undefined
      ) {
        let celda = document.createElement("td");
        let valorCelda = item[columna].toString(); // Convertir a cadena

        if (valorCelda.toLowerCase().endsWith(".png")) {
          let image = document.createElement("img");
          image.src = `img/Iconos/${item[columna]}`;
          image.alt = columna;
          image.width = 32; // Establecer ancho a 32
          image.height = 32; // Establecer alto a 32
          celda.appendChild(image);
        } else {
          celda.textContent = item[columna];
        }
        fila.appendChild(celda);
      }
    });

    // Crear celda para borrar
    let celdaBorrar = document.createElement("td");
    let botonBorrar = document.createElement("button");
    botonBorrar.textContent = "Borrar";

    // Agregar atributos de datos al botón de borrar
    botonBorrar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento
    botonBorrar.dataset.actividadId = item.idActividad; // Agregar atributo de datos con el idActividad
    botonBorrar.dataset.usuarioId = item.idUsuario; // Agregar atributo de datos con el idUsuario

    // Agregar clase de estilo para el botón de borrar
    botonBorrar.classList.add("boton-primario");

    // Crear celda para editar
    let celdaEditar = document.createElement("td");
    let botonEditar = document.createElement("button");
    botonEditar.textContent = "Editar";

    botonEditar.classList.add("boton-primario"); // Agregar clase para el estilo del botón
    botonEditar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento
    botonEditar.dataset.actividadId = item.idActividad; // Agregar atributo de datos con el idActividad
    botonEditar.dataset.usuarioId = item.idUsuario; // Agregar atributo de datos con el idUsuario

    // Agregar clase de estilo para el botón de editar
    botonEditar.classList.add("boton-secundario");

    celdaBorrar.appendChild(botonBorrar);
    fila.appendChild(celdaBorrar);

    celdaEditar.appendChild(botonEditar);
    fila.appendChild(celdaEditar);

    // Manejar diferentes casos de contenedor
    switch (idTabla.id) {
      case "usuarios-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el usuario
          EyBeditarUsuario(item);
        };
        botonBorrar.onclick = function () {
          // Lógica para eliminar el usuario
          EyBeliminarUsuario(item.idUsuario);
        };
        break;
      case "eventos-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el evento
          EyBeditarEvento(item);
        };
        botonBorrar.onclick = function () {
          // Lógica para eliminar el evento
          EyBeliminarEvento(item.idEvento);
        };
        break;
      case "actividades-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar la actividad
          EyBeditarActividad(item);
        };
        botonBorrar.onclick = function () {
          // Lógica para eliminar la actividad
          EyBeliminarActividad(item.idActividad);
        };
        break;
      // Agregar más casos según sea necesario
      default:
        botonEditar.addEventListener("click", function () {
          alert("Botón sin configurar");
        });
        botonBorrar.addEventListener("click", function () {
          alert("Botón sin configurar");
        });
    }

    tbody.appendChild(fila);
  });

  tabla.appendChild(tbody);
}

function EyBeditarUsuario(usuario) {
  // Lógica para editar un usuario
  console.log("Editar usuario", usuario);

  let servlet = "GestorUsuario";
  let action = "editarUsuario";
  let op = usuario.idUsuario;
  let formularioId = "editarUsuario.html";
  llamada(servlet, action, op, formularioId);
}

function EyBeliminarUsuario(idUsuario) {
  // Lógica para eliminar un usuario
  console.log("Eliminar usuario", idUsuario);
  fetch("GestorUsuario?action=eliminarUsuario&idUsuario=" + idUsuario).then(
    (response) => {
      if (response.ok) {
        console.log("Usuario eliminado correctamente, actualizo la lista");
        // Actualizar la lista después de eliminar
        AD_obtenerListadoUsuarios();
      } else {
        console.error("Error al eliminar el usuario");
      }
    }
  );
}

function EyBeditarEvento(evento) {
  // Lógica para editar un evento
  console.log("Editar evento", evento);

  let servlet = "GestorEvento";
  let action = "editarEvento";
  let op = evento.idEvento;
  let formularioId = "editarEvento.html";
  llamada(servlet, action, op, formularioId);
}

function EyBeliminarEvento(idEvento) {
  // Lógica para eliminar un evento
  console.log("Eliminar evento", idEvento);
  fetch("GestorEvento?action=eliminarEvento&idEvento=" + idEvento).then(
    (response) => {
      if (response.ok) {
        console.log("Evento eliminado correctamente, actualizo la lista");
        // Actualizar la lista después de eliminar
        AD_obtenerTodosLosEventosActivos();
        AD_obtenerPendientesAprobar();
        AD_obtenerPendientesPublicar();
      } else {
        console.error("Error al eliminar el evento");
      }
    }
  );
}

function EyBeditarActividad(actividad) {
  // Lógica para editar una actividad
  console.log("Editar actividad", actividad);

  let servlet = "GestorActividad";
  let action = "editarActividad";
  let op = actividad.idActividad;
  let formularioId = "editarActividad.html";
  llamada(servlet, action, op, formularioId);
}

function EyBeliminarActividad(idActividad) {
  // Lógica para eliminar una actividad
  console.log("Eliminar actividad", idActividad);
  fetch(
    "GestorActividad?action=eliminarActividad&idActividad=" + idActividad
  ).then((response) => {
    if (response.ok) {
      console.log("Actividad eliminada correctamente, actualizo la lista");
      // Actualizar la lista después de eliminar
      AD_obtenerActividades();
    } else {
      console.error("Error al eliminar la actividad");
    }
  });
}
