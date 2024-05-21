document.addEventListener('DOMContentLoaded', function() {
    const botonBuscarRechazados = document.getElementById('botonBuscarRechazados');
    if (botonBuscarRechazados) {
        botonBuscarRechazados.addEventListener('click', function(event) {
            event.preventDefault(); // Evitar el envío por defecto del formulario
            
            // Obtener la fecha actual y restarle 7 días para la fecha de inicio
            const fechaActual = new Date();
            const fechaInicio = new Date(fechaActual);
            fechaInicio.setDate(fechaInicio.getDate() - 7);
            const fechaInicioString = fechaInicio.toISOString().split('T')[0]; // Formato YYYY-MM-DD
            
            // Obtener la fecha actual para la fecha de fin
            const fechaFinString = fechaActual.toISOString().split('T')[0]; // Formato YYYY-MM-DD
            
            // Realizar la llamada al servlet para obtener los eventos rechazados
            fetch(`GestorEvento?action=obtenerEventosRechazados&fechaInicio=${fechaInicioString}&fechaFin=${fechaFinString}`)
                .then(response => response.json())
                .then(data => {
                    console.log("Eventos rechazados obtenidos:", data);

                    const contenedorTablaRechazados = document.getElementById("Rechazados-tabla");
                    pintarTabla(data, contenedorTablaRechazados);
                })
                .catch(error => {
                    console.error('Error al obtener eventos rechazados:', error);
                });
        });
    }
});
