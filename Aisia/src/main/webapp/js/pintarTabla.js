function pintarTabla(data, container) {
    let tabla = document.createElement('table');
    
    // Agregar clases de estilo a la tabla
    tabla.classList.add('tabla');

    // Limpiar la tabla antes de agregar nuevos datos
    container.innerHTML = '';

    // Verificar si hay datos y si el objeto data es un array
    if (data && Array.isArray(data) && data.length > 0) {
        // Crear encabezados de tabla solo para las columnas con datos
        let columnasConDatos = Object.keys(data[0]).filter(columna => {
            return data.some(item => item[columna] !== null && item[columna] !== undefined);
        });

        if (columnasConDatos.length > 0) {
            let thead = document.createElement('thead');
            let filaEncabezado = document.createElement('tr');
            columnasConDatos.forEach(columna => {
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
                columnasConDatos.forEach(columna => {
                    if (item[columna] !== null && item[columna] !== undefined) {
                        let celda = document.createElement('td');
                        if (item[columna].toLowerCase().endsWith('.png')) {
                            let image = document.createElement('img');
                            image.src = `img/Iconos/${item[columna]}`;
                            image.width = 32; // Establecer ancho a 32
                            image.height = 32; // Establecer alto a 32
                            image.alt = columna;
                            celda.appendChild(image);
                        } else {
                            celda.textContent = item[columna];
                        }
                        fila.appendChild(celda);
                    }
                });
                tbody.appendChild(fila);
            });
            tabla.appendChild(tbody);
        }
    }

    // Agregar tabla al contenedor
    container.appendChild(tabla);

}
