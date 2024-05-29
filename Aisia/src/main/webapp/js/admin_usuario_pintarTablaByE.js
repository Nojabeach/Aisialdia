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
        botonBorrar.dataset.idUsuario = item.idUsuario; 
        botonBorrar.classList.add('boton-primario');
        celdaBoton.appendChild(botonBorrar);

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idUsuario = item.idUsuario; 
        botonEditar.classList.add('boton-secundario');
        botonEditar.dataset.tabId = 'usuarios-editar'; // ID del tab a mostrar
        celdaBoton.appendChild(botonEditar);

        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    asignarUsuarioABorrar();
}

function asignarUsuarioABorrar() {
    document.querySelectorAll('#usuarios-tabla tbody .boton-primario, #usuarios-tabla tbody .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idUsuario = this.dataset.idUsuario; 
            let action = this.textContent.toLowerCase();

            if (action === 'borrar') {
                eliminarUsuario(idUsuario); // Cambiado a eliminarUsuario
            } else if (action === 'editar') {
                let tabId = this.dataset.tabId;
                editarUsuario(idUsuario, tabId); // Cambiado a editarUsuario
            }
        });
    });
}

function eliminarUsuario(idUsuario) { 
    console.log('Eliminando usuario', idUsuario);
    fetch('GestorUsuario', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarUsuario&idUsuario=${idUsuario}` 
    })
    .then(response => {
        if (response.ok) {
            console.log('Usuario eliminado correctamente, actualizo la lista');
            // Llama a la función para obtener y mostrar usuarios nuevamente
            AD_obtenerUsuarios();
        } else {
            console.error('Error al eliminar el usuario');
        }
    });
}



function editarUsuario(idUsuario, tabId) {
    USmostrarTab(tabId);
    let servlet = "GestorUsuario";
    let action = "obtenerINFOUsuario";
    let op = idUsuario;
    let metodo = "GET";
    let formularioId = "EDITusuariosForm";

    usuario_cargarFormularioDesdeServlet(servlet, action, op, formularioId, metodo);
}

function USmostrarTab(tabId) {
    const tab = document.getElementById(tabId);
    const allTabs = document.querySelectorAll('.usuarios-tab');

    allTabs.forEach(tab => {
        tab.style.display = 'none';
    });

    tab.style.display = 'block';
}
