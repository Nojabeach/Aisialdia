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
        if (clave.toLowerCase().endsWith('.png')) {
          let image = document.createElement('img');
          image.src = valor;
          image.alt = clave;
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
  alert("Botón sin configurar");
}

function EyBeliminarUsuario(idUsuario) {
  // Lógica para eliminar un usuario
  console.log("Eliminar usuario", idUsuario);
  alert("Botón sin configurar");
}

function EyBeditarEvento(evento) {
  // Lógica para editar un evento
  console.log("Editar evento", evento);
  alert("Botón sin configurar");
}

function EyBeliminarEvento(idEvento) {
  // Lógica para eliminar un evento
  console.log("Eliminar evento", idEvento);
  alert("Botón sin configurar");
}

function EyBeditarActividad(actividad) {
  // Lógica para editar una actividad
  console.log("Editar actividad", actividad);
  alert("Botón sin configurar");
}

function EyBeliminarActividad(idActividad) {
  // Lógica para eliminar una actividad
  console.log("Eliminar actividad", idActividad);
  alert("Botón sin configurar");
}
