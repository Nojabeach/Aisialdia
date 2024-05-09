function pintarTabla(data, container) {
    let tabla = document.createElement('table');

    // Limpiar la tabla antes de agregar nuevos datos
    container.innerHTML = '';

    // Crear encabezados de tabla
    let thead = document.createElement('thead');
    let filaEncabezado = document.createElement('tr');
    Object.keys(data[0]).forEach(columna => {
        let th = document.createElement('th');
        th.textContent = columna;
        filaEncabezado.appendChild(th);
    });
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
        tbody.appendChild(fila);
    });
    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Agregar clases de estilo a la tabla
    tabla.classList.add('tabla');
}
