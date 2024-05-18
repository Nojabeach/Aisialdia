function usuario_pintarTablaEditarYBorrar(data, container) {
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
        botonBorrar.dataset.idUsuario = item.idUsuario; // Cambiado a idUsuario
        botonBorrar.classList.add('boton-primario');
        celdaBoton.appendChild(botonBorrar);

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idUsuario = item.idUsuario; // Cambiado a idUsuario
        botonEditar.classList.add('boton-secundario');
        celdaBoton.appendChild(botonEditar);

        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    asignarUsuarioABorrar();
}

function asignarUsuarioABorrar() {
    document.querySelectorAll('.boton-primario, .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idUsuario = this.dataset.idUsuario; // Cambiado a idUsuario
            let action = this.textContent.toLowerCase();

            if (action === 'borrar') {
                eliminarUsuario(idUsuario); // Cambiado a eliminarUsuario
            } else if (action === 'editar') {
                editarUsuario(idUsuario); // Cambiado a editarUsuario
            }
        });
    });
}

function eliminarUsuario(idUsuario) { // Cambiado el nombre de la función
    console.log('Eliminando usuario', idUsuario);
    fetch('GestorUsuario', { // Cambiado a GestorUsuario
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarUsuario&idUsuario=${idUsuario}` // Cambiado a idUsuario
    })
    .then(response => {
        if (response.ok) {
            console.log('Usuario eliminado correctamente, actualizo la lista');
            // Llama a la función para obtener y mostrar usuarios nuevamente
            obtenerYMostrarUsuarios();
        } else {
            console.error('Error al eliminar el usuario');
        }
    });
}

function editarUsuario(idUsuario) { // Cambiado el nombre de la función
    //1º rellenar formulario y poner boton de editar
}
