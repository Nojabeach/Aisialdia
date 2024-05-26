function pintarTablaPublicar(data, container) {
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
    // Agregar una columna extra para el botón de publicar
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

        // Crear celda para publicar
        let celdaPublicar = document.createElement('td');
        let botonPublicar = document.createElement('button');
        botonPublicar.textContent = 'Publicar';
        botonPublicar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento

        // Agregar clase de estilo para el botón de publicar
        botonPublicar.classList.add('boton-primario');

        celdaPublicar.appendChild(botonPublicar);
        fila.appendChild(celdaPublicar);

        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Asignar evento de publicacion a los botones
    asignarEventoPublicar();
}

// Asignar evento de publicacion a los botones
function asignarEventoPublicar() {
    document.querySelectorAll('#PendientesPublicar-tabla tbody .boton-primario').forEach(boton => {
        boton.addEventListener('click', function () {
            let idEvento = this.dataset.eventoId;
            console.log('Asignar evento a publicar', idEvento);
            publicarEvento(idEvento);
        });
    });
}

function publicarEvento(idEvento){
    console.log('Publicando evento', idEvento);
    fetch('GestorEvento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=publicarEvento&idEvento=${idEvento}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Evento publicado correctamente, actualizo la lista');
                // Actualizar la lista después de publicar: tanto en eventos como en perfil
                window.location.reload();
            } else {
                console.error('Error al publicar el evento');
            }
        });
}
