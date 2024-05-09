function pintarTablaSoloBorrar(data, container) {
    let tabla = document.createElement('table');

    // Limpiar la tabla antes de agregar nuevos datos
    container.innerHTML = '';

    // Crear encabezados de tabla
    let thead = document.createElement('thead');
    let filaEncabezado = document.createElement('tr');
    Object.keys(data[0]).forEach(columna => {
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
        Object.entries(item).forEach(([clave, valor]) => {
            if (valor !== null && valor !== undefined) {
                let celda = document.createElement('td');
                celda.textContent = valor;
                fila.appendChild(celda);
            }
        });

        // Crear celda para borrar
        let celdaBorrar = document.createElement('td');
        let botonBorrar = document.createElement('button');
        botonBorrar.textContent = 'Borrar';
        
        // Manejar diferentes casos de contenedor
        switch (container.id) {
            case 'favoritos-tabla':
                botonBorrar.onclick = function() {
                    // Lógica para borrar el elemento solo si el contenedor es el de favoritos
                    eliminarFavorito(item.idEvento); // Llamar a la función eliminarFavorito pasando el idEvento
                };
                break;
            // Agregar más casos según sea necesario
            default:
                botonBorrar.addEventListener('click', function() {
                    alert("Botón sin configurar");
                });
        }
        
        celdaBorrar.appendChild(botonBorrar);
        fila.appendChild(celdaBorrar);

        tbody.appendChild(fila);
    });

    tabla.appendChild(tbody);

    // Agregar tabla al contenedor
    container.appendChild(tabla);

    // Agregar clases de estilo a la tabla
    tabla.classList.add('tabla');
}

function eliminarFavorito(idEvento) {
    fetch('GestorFavorito?action=eliminarFavorito&idEvento=' + idEvento)
    .then(response => {
        if(response.ok) {
            // Actualizar la lista de favoritos después de eliminar
            obtenerFavoritos();
        } else {
            console.error('Error al eliminar el favorito');
        }
    });
} 
