
function pintarTablaAgregarFavorito(data, container) {
    let tabla = document.createElement('table');
    tabla.classList.add('tabla');

    container.innerHTML = ''; // Limpiar la tabla antes de agregar nuevos datos

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
    // Agregar una columna extra para el botón de agregar
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
                console.log("Fuera_Ruta de la imagen:", `img/Iconos/${valorCelda}`); 
                if (valorCelda.toLowerCase().endsWith('.png')) {
                    let image = document.createElement('img');
                    image.src = `img/Iconos/${item[columna]}`;
                    console.log("Ruta de la imagen:", image.src); // Log de la ruta de la imagen
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

        // Crear celda para agregar
        let celdaAgregar = document.createElement('td');
        let botonAgregar = document.createElement('button');
        botonAgregar.textContent = 'Agregar';
        botonAgregar.dataset.eventoId = item.idEvento; // Agregar atributo de datos con el idEvento

        // Agregar clase de estilo para el botón de agregar
        botonAgregar.classList.add('boton-primario');

        celdaAgregar.appendChild(botonAgregar);
        fila.appendChild(celdaAgregar);

        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Asignar evento de agregar a los botones
    asignarEventoFavoritoAgregar();
}

function asignarEventoFavoritoAgregar() {
    document.querySelectorAll('.boton-primario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.eventoId;
            console.log('Asignar evento a agregar', idEvento);
            agregarEventoFavorito(idEvento);
        });
    });
}

function agregarEventoFavorito(idEvento) {
    console.log('Agregando evento', idEvento);
    fetch('GestorFavorito', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=agregarFavorito&idEvento=${idEvento}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Evento agregado correctamente, actualizo la lista');
                // Actualizar la lista después de agregar
                obtenerFavoritos();
            } else {
                console.error('Error al agregar el evento');
            }
        });
}
