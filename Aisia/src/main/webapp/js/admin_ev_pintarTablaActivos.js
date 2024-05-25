function EVAdmin_pintarTablaEditarYBorrar(data, container) {
    let tabla = document.createElement('table');
    tabla.classList.add('tabla');
    container.innerHTML = '';

    let columnasConDatos = Object.keys(data[0]).filter(columna => 
        data.some(item => item[columna] !== 0 && item[columna] !== null && item[columna] !== undefined)
    );

    let thead = document.createElement('thead');
    let filaEncabezado = document.createElement('tr');
    columnasConDatos.forEach(columna => {
        let th = document.createElement('th');
        th.textContent = columna;
        filaEncabezado.appendChild(th);
    });
    let thAcciones = document.createElement('th');
    thAcciones.classList.add('boton-header');
    thAcciones.textContent = 'Acciones';
    filaEncabezado.appendChild(thAcciones);
    thead.appendChild(filaEncabezado);
    tabla.appendChild(thead);

    let tbody = document.createElement('tbody');
    data.forEach(item => {
        let fila = document.createElement('tr');
        fila.dataset.eventId = item.idEvento;

        columnasConDatos.forEach(columna => {
            let celda = document.createElement('td');
            let valorCelda = item[columna];

            if (valorCelda && valorCelda.toString().toLowerCase().endsWith('.png')) {
                let image = document.createElement('img');
                image.src = `img/Iconos/${valorCelda}`;
                image.alt = columna;
                image.width = 32;
                image.height = 32;
                celda.appendChild(image);
            } else {
                celda.textContent = valorCelda !== null ? valorCelda : '';
            }
            fila.appendChild(celda);
        });

        let celdaBoton = document.createElement('td');
        celdaBoton.classList.add('boton-contenedor');

        let botonBorrar = document.createElement('button');
        botonBorrar.textContent = 'Borrar';
        botonBorrar.dataset.idEvento = item.idEvento;
        botonBorrar.classList.add('boton-primario');

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idEvento = item.idEvento;
        botonEditar.classList.add('boton-secundario');
        botonEditar.dataset.tabId = 'eventos-editar';

        celdaBoton.appendChild(botonBorrar);
        celdaBoton.appendChild(botonEditar);
        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    EVAdmin_asignarEventoABorrar();
    EVAdmin_asignarEventoAEditar();
}

function EVAdmin_asignarEventoABorrar() {
    document.querySelectorAll('.boton-primario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.idEvento;
            EVAdmin_eliminarEvento(idEvento);
        });
    });
}

function EVAdmin_asignarEventoAEditar() {
    document.querySelectorAll('.boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idEvento = this.dataset.idEvento;
            let tabId = this.dataset.tabId;
            EVAdmin_editarEvento(idEvento, tabId);
        });
    });
}

function EVAdmin_eliminarEvento(idEvento) {
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
            AD_obtenerPendientesAprobar();
            AD_obtenerPendientesPublicar();
        } else {
            console.error('Error al eliminar el evento');
        }
    });
}

function EVAdmin_editarEvento(idEvento, tabId) {
    mostrarTab(tabId);
    let servlet = "GestorEvento";
    let action = "obtenerEventosConActividad";
    let op = idEvento;
    let metodo = "GET";
    let formularioId = "EDITeventosForm";

    evento_cargarFormularioDesdeServlet(servlet, action, op, formularioId, metodo);
}

function EVAdmin_mostrarTab(tabId) {
    const tab = document.getElementById(tabId);
    const allTabs = document.querySelectorAll('.evento-tab');

    allTabs.forEach(tab => {
        tab.style.display = 'none';
    });

    tab.style.display = 'block';
}
