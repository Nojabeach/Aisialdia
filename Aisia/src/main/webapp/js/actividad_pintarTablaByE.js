

function actividad_pintarTablaEditarYBorrar(data, container) {
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
                    console.log(image.src);
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
        botonBorrar.dataset.idActividad = item.idActividad;
        botonBorrar.classList.add('boton-primario');
        celdaBoton.appendChild(botonBorrar);

        let botonEditar = document.createElement('button');
        botonEditar.textContent = 'Editar';
        botonEditar.dataset.idActividad = item.idActividad;
        botonEditar.classList.add('boton-secundario');
        botonEditar.dataset.tabId = 'actividades-editar'; // ID del tab a mostrar
        celdaBoton.appendChild(botonEditar);

        // Asigna evento al botón Editar
        botonEditar.addEventListener('click', function() {
            let idActividad = this.dataset.idActividad;
            console.log('Editar actividad', idActividad);
            let tabId = this.dataset.tabId;
            console.log('Editar en tab', tabId);
            editarActividad(idActividad, tabId);
        });
        
        fila.appendChild(celdaBoton);
        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);
    container.appendChild(tabla);

    asignarActividadABorrar();
}

function asignarActividadABorrar() {
    document.querySelectorAll('.boton-primario, .boton-secundario').forEach(boton => {
        boton.addEventListener('click', function() {
            let idActividad = this.dataset.idActividad;
            let action = this.textContent.toLowerCase();

            if (action === 'borrar') {
                eliminarActividad(idActividad);
            } else if (action === 'editar') {
                let tabId = this.dataset.tabId;
                editarActividad(idActividad, tabId); // Pasa el tabId aquí
            }
        });
    });
}

function eliminarActividad(idActividad) {
    console.log('Eliminando actividad', idActividad);
    fetch('GestorActividad', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=eliminarActividad&idActividad=${idActividad}`
    })
    .then(response => {
        if (response.ok) {
            console.log('Actividad eliminada correctamente, actualizo la lista');
            AD_obtenerActividades();
        } else {
            console.error('Error al eliminar el evento');
        }
    });
}

function editarActividad(idActividad, tabId) {
    // Mostrar el tab correspondiente
    mostrarTab(tabId);
    let servlet = "GestorActividad";
    let action = "obtenerActividadporID";
    let op = idActividad;
    let metodo = "GET";
    let formularioId = "EDITactividadesForm";
    console.log('Editar actividad', idActividad);
    actividad_cargarFormularioDesdeServlet(servlet, action, op, formularioId, metodo);
}

function mostrarTab(tabId) {
    const tab = document.getElementById(tabId);
    const allTabs = document.querySelectorAll('.actividades-tab'); 

    allTabs.forEach(tab => {
        tab.style.display = 'none';
    });

    tab.style.display = 'block';
}

