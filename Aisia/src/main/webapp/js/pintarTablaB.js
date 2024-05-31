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
    console.log('FAV_Eliminando evento', idEvento);
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
                // Obtener la URL actual
                const currentUrl = new URL(window.location.href);
                        console.log (currentUrl);
                // Comparar la URL actual con la URL de perfil.html
                if (currentUrl.pathname === '/Aisia/perfil.html') {
                    // Si estás en perfil.html, ejecutar perfil_obtenerFavoritos
                    perfil_obtenerFavoritos();
                } else if (currentUrl.pathname === '/Aisia/eventos.html') {
                    // Si estás en eventos.html, ejecutar eventos_obtenerFavoritos
                    PTobtenerFavoritos();
                } else {
                    // Si no estás en ninguna de las dos páginas, no hacer nada
                    console.warn('No se encontró la función correspondiente para actualizar la lista de favoritos');
                }
            } else {
                console.error('Error al eliminar el evento');
            }
        });
}
function  PTobtenerFavoritos() {
    console.log("FAV_PT_Obteniendo favoritos");
    fetch("GestorFavorito?action=obtenerEventosFavoritos")
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        const contenedorAPintar = document.getElementById("favoritos-tabla");
        pintarTablaSoloBorrar_Favorito(data, contenedorAPintar);
        console.log("Pintando favoritos : pintarTablaB.js");
      });
  }