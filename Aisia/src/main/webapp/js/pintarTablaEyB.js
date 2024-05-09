function pintarTablaEyB(data, idTabla) {
  let tabla = document.getElementById(idTabla);

  // Limpiar la tabla antes de agregar nuevos datos
  while (tabla.firstChild) {
    tabla.removeChild(tabla.firstChild);
  }

  // Crear encabezados de tabla
  let thead = document.createElement("thead");
  let filaEncabezado = document.createElement("tr");
  Object.keys(data[0]).forEach((columna) => {
    let th = document.createElement("th");
    th.textContent = columna;
    filaEncabezado.appendChild(th);
  });
  // Agregar columnas adicionales para editar y borrar
  filaEncabezado.innerHTML += "<th>Editar</th><th>Borrar</th>";
  thead.appendChild(filaEncabezado);
  tabla.appendChild(thead);

  // Crear cuerpo de tabla
  let tbody = document.createElement('tbody');
  data.forEach(item => {
      let fila = document.createElement('tr');
      Object.entries(item).forEach(([clave, valor]) => {
          if (valor !== null && valor !== undefined) {
              let celda = document.createElement('td');
              celda.textContent = valor;
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
          // Lógica para editar el elemento en la tabla de favoritos
          editarUsuario(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar el elemento en la tabla de favoritos
          eliminarUsuario(item.id);
        };
      case "eventos-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el elemento en la tabla de favoritos
          editarEvento(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar el elemento en la tabla de favoritos
          eliminarEvento(item.id);
        };
      case "actividades-tabla":
        botonEditar.onclick = function () {
          // Lógica para editar el elemento en la tabla de favoritos
          editarActividad(item);
        };
        botonEliminar.onclick = function () {
          // Lógica para eliminar el elemento en la tabla de favoritos
          eliminarActividad(item.id);
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

  // Agregar clases de estilo a la tabla
  tabla.classList.add("tabla");
}


function editarUsuario(idUsuario) {
  // Lógica para editar un usuario
  console.log("Editar usuario", idUsuario);
  alert("Botón sin configurar");
}
function eliminarUsuario(idUsuario) { 
  // Lógica para eliminar un usuario
  console.log("Eliminar usuario", idUsuario);
  alert("Botón sin configurar");
}
function editarEvento(idEvento) {
  // Lógica para editar un evento
  console.log("Editar evento", idEvento);
  alert("Botón sin configurar");
}
function eliminarEvento(idEvento) {
  // Lógica para eliminar un evento
  console.log("Eliminar evento", idEvento);
  alert("Botón sin configurar");
}
function editarActividad(idActividad) {
  // Lógica para editar una actividad
  console.log("Editar actividad", idActividad);
  alert("Botón sin configurar");
}
function eliminarActividad(idActividad) {
  // Lógica para eliminar una actividad
  console.log("Eliminar actividad", idActividad);
  alert("Botón sin configurar");
}
