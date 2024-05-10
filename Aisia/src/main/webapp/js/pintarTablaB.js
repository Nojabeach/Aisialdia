function pintarTablaSoloBorrar_Favorito(data, container) {
    let tabla = document.createElement('table');
    // Agregar clases de estilo a la tabla
    tabla.classList.add('tabla');

    
    // Limpiar la tabla antes de agregar nuevos datos
    container.innerHTML = '';

    // Crear encabezados de tabla solo para las columnas con datos
    let columnasConDatos = Object.keys(data[0]).filter(columna => {
        return data.some(item => item[columna] !== 0 && item[columna] !== null && item[columna] !== undefined);
    });

    let thead = document.createElement('thead');
    let filaEncabezado = document.createElement('tr');
    columnasConDatos.forEach(columna => {
        let th = document.createElement('th');
        th.textContent = columna;
        filaEncabezado.appendChild(th);
    });
    // Agregar una columna extra para el botón de borrar
    filaEncabezado.innerHTML += "<th>Acciones</th>";
    thead.appendChild(filaEncabezado);
    tabla.appendChild(thead);

    // Crear cuerpo de tabla
    let tbody = document.createElement('tbody');
    data.forEach(item => {
        let fila = document.createElement('tr');

        columnasConDatos.forEach(columna => {
            if (item[columna] !== 0 && item[columna] !== null && item[columna] !== undefined) {
                let celda = document.createElement('td');
                if (columna.toLowerCase().endsWith('.png')) {
                    let image = document.createElement('img');
                    image.src = `img/Iconos/${item[columna]}.png`;
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
        let celdaBorrar = document.createElement('td');
        let botonBorrar = document.createElement('button');
        botonBorrar.textContent = 'Borrar';
        botonBorrar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento

        // Agregar clase de estilo para el botón de borrar
        botonBorrar.classList.add('boton-primario');

        celdaBorrar.appendChild(botonBorrar);
        fila.appendChild(celdaBorrar);

        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Asignar evento de borrado a los botones
    asignarEventoFavoritoBorrar();
}

// Asignar evento de borrado a los botones
function asignarEventoFavoritoBorrar() {
    document.querySelectorAll('.boton-primario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.eventoId;
            console.log('Asignar evento a borrar', idEvento);
            eliminarEventoFavorito(idEvento);
        });
    });
}

function eliminarEventoFavorito(idEvento) {
    console.log('Eliminando evento', idEvento);
    fetch('GestorFavorito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarFavorito&idEvento=${idEvento}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Evento eliminado correctamente, actualizo la lista');
                // Actualizar la lista después de eliminar
                obtenerFavoritos();
            } else {
                console.error('Error al eliminar el evento');
            }
        });
}
