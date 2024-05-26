function pintarTablaAprobarRechazar(data, container) {
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
    // Agregar una columna extra para los botones de acciones
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
                let valorCelda = item[columna].toString(); // Convertir a cadena

                if (valorCelda.toLowerCase().endsWith('.png')) {
                    let image = document.createElement('img');
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

        // Crear celda para acciones
        let celdaAcciones = document.createElement('td');

        // Botón Aprobar
        let botonAprobar = document.createElement('button');
        botonAprobar.textContent = 'Aprobar';
        botonAprobar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento
        botonAprobar.classList.add('boton-primario');
        celdaAcciones.appendChild(botonAprobar);

        // Botón Rechazar
        let botonRechazar = document.createElement('button');
        botonRechazar.textContent = 'Rechazar';
        botonRechazar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento
        botonRechazar.classList.add('boton-secundario');
        celdaAcciones.appendChild(botonRechazar);

        fila.appendChild(celdaAcciones);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Asignar eventos de aprobación y rechazo a los botones
    asignarEventosAprobarRechazar();
}

// Asignar eventos de aprobación y rechazo a los botones
function asignarEventosAprobarRechazar() {

    document.querySelectorAll('#PendientesAprobar-tabla tbody .boton-primario').forEach(boton => {
        boton.addEventListener('click', function () {
            let idEvento = this.dataset.eventoId;
            console.log('Asignar evento a aprobar', idEvento);
            aprobarEvento(idEvento);
        });
    });

    document.querySelectorAll('#PendientesAprobar-tabla tbody .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function () {
            let idEvento = this.dataset.eventoId;
            console.log('Asignar evento a rechazar', idEvento);
            rechazarEvento(idEvento);
        });
    });
}

function aprobarEvento(idEvento){
    console.log('Aprobando evento', idEvento);
    fetch('GestorEvento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=aprobarPublicacionEvento&idEvento=${idEvento}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Evento aprobado correctamente, actualizo la lista');
                AD_obtenerPendientesAprobar();
            } else {
                console.error('Error al aprobar el evento');
            }
        });
}

function rechazarEvento(idEvento){
    console.log('Rechazando evento', idEvento);
    fetch('GestorEvento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=rechazarEvento&idEvento=${idEvento}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Evento rechazado correctamente, actualizo la lista');
                AD_obtenerPendientesAprobar();
            } else {
                console.error('Error al rechazar el evento');
            }
        });
}
