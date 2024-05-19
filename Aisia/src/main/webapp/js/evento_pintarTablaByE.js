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
        fila.dataset.eventId = item.idEvento; // Añadir el atributo data-event-id a cada fila

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
        botonBorrar.dataset.idEvento = item.idEvento;
        botonBorrar.classList.add('boton-primario');
        celdaBoton.appendChild(botonBorrar);

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idEvento = item.idEvento;
        botonEditar.classList.add('boton-secundario');
        botonEditar.dataset.tabId = 'eventos-editar'; // ID del tab a mostrar
        celdaBoton.appendChild(botonEditar);

// Asigna evento al botón Editar
botonEditar.addEventListener('click', function() {
    let idEvento = this.dataset.idEvento;
    console.log('Editar evento', idEvento);
    let tabId = this.dataset.tabId;
    console.log('Editar en tab', tabId);
    editarEvento(idEvento, tabId);
});


        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    asignarEventoABorrar();
}

function asignarEventoABorrar() {
    document.querySelectorAll('.boton-primario, .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.idEvento;
            let action = this.textContent.toLowerCase();

            if (action === 'borrar') {
                eliminarEvento(idEvento);
            } else if (action === 'editar') {
                editarEvento(idEvento);
            }
        });
    });
}

function eliminarEvento(idEvento) {
    console.log('Eliminando evento', idEvento);
    fetch('GestorEvento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarEvento&idEvento=${idEvento}`
    })
    .then(response => {
        if (response.ok) {
            console.log('Evento eliminado correctamente, actualizo la lista');
            AD_obtenerEventos();
        } else {
            console.error('Error al eliminar el evento');
        }
    })
}

function editarEvento(idEvento, tabId) {
    // Mostrar el tab correspondiente
    mostrarTab(tabId);
    let servlet = "GestorEvento";
    let action = "obtenerEventoporID";
    let op = idEvento;
    let metodo = "GET";
    let formularioId = "EDITeventoForm";

    actividad_cargarFormularioDesdeServlet(servlet, action, op, formularioId, metodo);
}

function mostrarTab(tabId) {
    const tab = document.getElementById(tabId);
    const allTabs = document.querySelectorAll('.evento-tab'); 

    allTabs.forEach(tab => {
        tab.style.display = 'none';
    });

    tab.style.display = 'block';
}