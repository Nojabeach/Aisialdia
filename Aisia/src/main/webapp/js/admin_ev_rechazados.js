document.addEventListener('DOMContentLoaded', function() {
    // Obtener los elementos de fecha
    const fechaInicioInput = document.getElementById('fechaInicio');
    const fechaFinInput = document.getElementById('fechaFin');

    // Obtener la fecha actual y restarle 7 días para la fecha de inicio
    const fechaActual = new Date();
    const fechaInicio = new Date(fechaActual);
    fechaInicio.setDate(fechaInicio.getDate() - 7);
    const fechaInicioString = fechaInicio.toISOString().split('T')[0]; // Formato YYYY-MM-DD
    const fechaFinString = fechaActual.toISOString().split('T')[0]; // Formato YYYY-MM-DD

    // Establecer los valores de los campos de fecha
    fechaInicioInput.value = fechaInicioString;
    fechaFinInput.value = fechaFinString;

    // Añadir el listener para el botón de búsqueda
    const botonBuscarRechazados = document.getElementById('botonBuscarRechazados');
    if (botonBuscarRechazados) {
        botonBuscarRechazados.addEventListener('click', function(event) {
            event.preventDefault(); // Evitar el envío por defecto del formulario

            // Obtener las fechas seleccionadas por el usuario
            const fechaInicioSeleccionada = fechaInicioInput.value;
            const fechaFinSeleccionada = fechaFinInput.value;

            console.log("Fecha inicio:", fechaInicioSeleccionada);
            console.log("Fecha fin:", fechaFinSeleccionada);

            // Realizar la llamada al servlet para obtener los eventos rechazados
            fetch(`GestorEvento?action=obtenerEventosRechazados&fechaInicio=${fechaInicioSeleccionada}&fechaFin=${fechaFinSeleccionada}`)
                .then(response => response.json())
                .then(data => {
                    console.log("Eventos rechazados obtenidos:", data);

                    const contenedorTablaRechazados = document.getElementById("Rechazados-tabla");
                    pintarTablaRechazados(data, contenedorTablaRechazados);
                })
                .catch(error => {
                    console.error('Error al obtener eventos rechazados:', error);
                });
        });
    }
});
