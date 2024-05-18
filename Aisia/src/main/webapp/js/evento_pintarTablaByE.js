function evento_pintarTablaEditarYBorrar(data, container) {
    let tabla = document.createElement('table');
    tabla.classList.add('tabla');

    container.innerHTML = '';

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
    filaEncabezado.innerHTML += "<th class='boton-header'>Acciones</th>";
    thead.appendChild(filaEncabezado);
    tabla.appendChild(thead);

    let tbody = document.createElement('tbody');
    data.forEach(item => {
        let fila = document.createElement('tr');

        columnasConDatos.forEach(columna => {
            if (item[columna] !== 0 && item[columna] !== null && item[columna] !== undefined) {
                let celda = document.createElement('td');
                let valorCelda = item[columna].toString();

                if (valorCelda.toLowerCase().endsWith('.png')) {
                    let image = document.createElement('img');
                    image.src = `img/Iconos/${item[columna]}`;
                    image.alt = columna;
                    image.width = 32;
                    image.height = 32;
                    celda.appendChild(image);
                } else {
                    celda.textContent = item[columna];
                }
                fila.appendChild(celda);
            }
        });

        let celdaBoton = document.createElement('td');
        celdaBoton.classList.add('boton-contenedor');

        let botonBorrar = document.createElement('button');
        botonBorrar.textContent = 'Borrar';
        botonBorrar.dataset.idEvento = item.idEvento; // Cambiar a idEvento
        botonBorrar.classList.add('boton-primario');
        celdaBoton.appendChild(botonBorrar);

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idEvento = item.idEvento; // Cambiar a idEvento
        botonEditar.classList.add('boton-secundario');
        celdaBoton.appendChild(botonEditar);

        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    asignarEventoABorrar(); // Cambiar a asignarEventoABorrar
}

function asignarEventoABorrar() { // Cambiar a asignarEventoABorrar
    document.querySelectorAll('.boton-primario, .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.idEvento; // Cambiar a idEvento
            let action = this.textContent.toLowerCase();

            if (action === 'borrar') {
                eliminarEvento(idEvento); // Cambiar a eliminarEvento
            } else if (action === 'editar') {
                editarEvento(idEvento); // Cambiar a editarEvento
            }
        });
    });
}

function eliminarEvento(idEvento) { // Cambiar a eliminarEvento
    console.log('Eliminando evento', idEvento); // Cambiar a evento
    fetch('GestorEvento', { // Cambiar a GestorEvento
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarEvento&idEvento=${idEvento}` // Cambiar a idEvento y eliminarEvento
    })
    .then(response => {
        if (response.ok) {
            console.log('Evento eliminado correctamente, actualizo la lista'); // Cambiar a evento
            AD_obtenerEventos(); // Cambiar a AD_obtenerEventos
        } else {
            console.error('Error al eliminar el evento');
        }
    })
}

function editarEvento(idEvento) { // Cambiar a editarEvento
    //1º rellenar formulario y poner boton de editar
}
