function pintarTablaEyB(data, idTabla) {
  let tabla = document.getElementById(idTabla);

  // Agregar clases de estilo a la tabla
  tabla.classList.add("tabla");

  // Limpiar la tabla antes de agregar nuevos datos
  while (tabla.firstChild) {
    tabla.removeChild(tabla.firstChild);
  }

  // Verificar si hay datos para mostrar
  if (data.length === 0) {
    return; // No hay datos, salir sin crear la tabla
  }

  // Crear encabezados de tabla si hay datos
  let thead = document.createElement("thead");
  let filaEncabezado = document.createElement("tr");
  let columnasConDatos = {};

  // Identificar las columnas con datos
  Object.keys(data[0]).forEach((columna) => {
    columnasConDatos[columna] = false; // Inicializar todas las columnas como sin datos
  });

  // Recorrer los datos para determinar las columnas con datos
  data.forEach((item) => {
    Object.entries(item).forEach(([clave, valor]) => {
      if (valor !== null && valor !== undefined && valor !== "") {
        columnasConDatos[clave] = true; // Marcar la columna como con datos si encuentra al menos un valor
      }
    });
  });

  // Crear encabezados solo para las columnas con datos
  Object.entries(columnasConDatos).forEach(([columna, tieneDatos]) => {
    if (tieneDatos) {
      let th = document.createElement("th");
      th.textContent = columna;
      filaEncabezado.appendChild(th);
    }
  });

  // Agregar columnas adicionales para editar y borrar
  filaEncabezado.innerHTML += "<th>Editar</th><th>Borrar</th>";
  thead.appendChild(filaEncabezado);
  tabla.appendChild(thead);

  // Crear cuerpo de tabla
  let tbody = document.createElement("tbody");
  data.forEach((item) => {
    let fila = document.createElement("tr");
    Object.entries(item).forEach(([clave, valor]) => {
      if (
        valor !== null &&
        valor !== undefined &&
        valor !== "" &&
        columnasConDatos[clave]
      ) {
        let celda = document.createElement("td");
        if (valor.toLowerCase().endsWith('.png'))  {
          let image = document.createElement('img');
          image.src = `img/Iconos/${valor}`;
          image.alt = clave;
          image.width = 32; // Establecer ancho a 32
          image.height = 32; // Establecer alto a 32
          celda.appendChild(image);
        } else {
          celda.textContent = valor;
        }
        fila.appendChild(celda);
      }
    });

    // Crear celda para editar
    let celdaEditar = document.createElement("td");
    let botonEditar = document.createElement("button");
    botonEditar.textContent = "Editar";
    botonEditar.classList.add("boton-primario"); // Agregar clase para el estilo del botón

    // Crear celda para eliminar
    let celdaEliminar = document.createElement("td");
    let botonEliminar = document.createElement("button");
    botonEliminar.textContent = "Eliminar";
    botonEliminar.classList.add("boton-secundario"); // Agregar clase para el estilo del botón

    // Manejar diferentes casos de contenedor
    switch (tabla.id) {
      case "usuarios-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el usuario
          EyBeditarUsuario(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar el usuario
          EyBeliminarUsuario(item.id);
        };
        break;
      case "eventos-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el evento
          EyBeditarEvento(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar el evento
          EyBeliminarEvento(item.id);
        };
        break;
      case "actividades-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar la actividad
          EyBeditarActividad(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar la actividad
          EyBeliminarActividad(item.id);
        };
        break;
      // Agregar más casos según sea necesario
      default:
        botonEditar.addEventListener("click", function () {
          alert("Botón sin configurar");
        });
        botonEliminar.addEventListener("click", function () {
          alert("Botón sin configurar");
        });
    }

    celdaEditar.appendChild(botonEditar);
    fila.appendChild(celdaEditar);

    celdaEliminar.appendChild(botonEliminar);
    fila.appendChild(celdaEliminar);

    tbody.appendChild(fila);
  });

  tabla.appendChild(tbody);

    
}

function EyBeditarUsuario(usuario) {
  // Lógica para editar un usuario
  console.log("Editar usuario", usuario);
 
  let servlet = 'GestorUsuario';
  let action = 'editarUsuario';
  let op = getParameterByName("idUsuario");
  let formularioId = 'editarUsuario.html';
  llamada(servlet, action, op, formularioId);
}

function EyBeliminarUsuario(idUsuario) {
  // Lógica para eliminar un usuario
  console.log("Eliminar usuario", idUsuario);
  fetch('GestorUsuario?action=eliminarUsuario&idUsuario=' + idUsuario)
  .then(response => {
      if (response.ok) {
          console.log('Usuario eliminado correctamente, actualizo la lista');
          // Actualizar la lista después de eliminar
          AD_obtenerListadoUsuarios();
      } else {
          console.error('Error al eliminar el usuario');
      }
  });
}

function EyBeditarEvento(evento) {
  // Lógica para editar un evento
  console.log("Editar evento", evento);
 
  let servlet = 'GestorEvento';
  let action = 'editarEvento';
  let op = getParameterByName("idEvento");
  let formularioId = 'editarEvento.html';
  llamada(servlet, action, op, formularioId);
}

function EyBeliminarEvento(idEvento) {
  // Lógica para eliminar un evento
  console.log("Eliminar evento", idEvento);
  fetch('GestorEvento?action=eliminarEvento&idEvento=' + idEvento)
  .then(response => {
      if (response.ok) {
          console.log('Evento eliminado correctamente, actualizo la lista');
          // Actualizar la lista después de eliminar

          AD_obtenerTodosLosEventosActivos();
          AD_obtenerPendientesAprobar();
          AD_obtenerPendientesPublicar();

      } else {
          console.error('Error al eliminar el evento');
      }
  });
}

function EyBeditarActividad(actividad) {
  // Lógica para editar una actividad
  console.log("Editar actividad", actividad);

  let servlet = 'GestorActividad';
  let action = 'editarActividad';
  let op = getParameterByName("actividad");
  let formularioId = 'editarActividad.html'; 
  llamada(servlet, action, op, formularioId);
}


function EyBeliminarActividad(idActividad) {
  // Lógica para eliminar una actividad
  console.log("Eliminar actividad", idActividad);
  fetch('GestorActividad?action=eliminarActividad&idActividad=' + idActividad)
  .then(response => {
      if (response.ok) {
          console.log('Actividad eliminada correctamente, actualizo la lista');
          // Actualizar la lista después de eliminar
          AD_obtenerActividades();
      } else {
          console.error('Error al eliminar el evento');
      }
  });
}
