
function pintarTabla(data, idTabla) {
    let tabla = document.getElementById(idTabla);

    // Limpiar la tabla antes de agregar nuevos datos
    while (tabla.firstChild) {
        tabla.removeChild(tabla.firstChild);
    }

    // Crear encabezados de tabla
    let thead = document.createElement("thead");
    let filaEncabezado = document.createElement("tr");
    Object.keys(data[0]).forEach(columna => {
        let th = document.createElement("th");
        th.textContent = columna;
        filaEncabezado.appendChild(th);
    });
    thead.appendChild(filaEncabezado);
    tabla.appendChild(thead);

    // Crear cuerpo de tabla
    let tbody = document.createElement("tbody");
    data.forEach(item => {
        let fila = document.createElement("tr");
        Object.values(item).forEach(valor => {
            let celda = document.createElement("td");
            celda.textContent = valor;
            fila.appendChild(celda);
        });

        // Crear celda para editar
        let celdaEditar = document.createElement("td");
        let botonEditar = document.createElement("button");
        botonEditar.textContent = "Editar";
        botonEditar.onclick = function() {
            // Lógica para editar el elemento
            console.log("Editando", item);
        };
        celdaEditar.appendChild(botonEditar);
        fila.appendChild(celdaEditar);

        // Crear celda para eliminar
        let celdaEliminar = document.createElement("td");
        let botonEliminar = document.createElement("button");
        botonEliminar.textContent = "Eliminar";
        botonEliminar.onclick = function() {
            // Lógica para eliminar el elemento
            console.log("Eliminando", item);
        };
        celdaEliminar.appendChild(botonEliminar);
        fila.appendChild(celdaEliminar);

        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
}
