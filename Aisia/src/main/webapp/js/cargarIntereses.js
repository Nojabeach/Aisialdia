/*
 * Este script se encarga de cargar los nombres de intereses por defecto en un formulario de registro.
 * Utiliza Fetch API para obtener los datos del servidor y luego los agrega dinámicamente a un elemento select HTML .
 */

// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', () => {
    // Mensaje de depuración para verificar que el evento se ha disparado
    console.log('Evento DOMContentLoaded disparado');

    // Obtener el elemento select donde se cargarán los intereses
    const interesesSelect = document.getElementById('intereses');
    const interesesSeleccionadosText = document.getElementById('interesesSeleccionados-text');

    // Realizar una solicitud Fetch al servidor para obtener los datos de intereses
    fetch('GestorIntereses?accion=listar')
        .then(response => {
            // Mensaje de depuración para verificar la respuesta del servidor
            console.log('Respuesta de la solicitud Fetch:', response);
            // Convertir la respuesta a JSON
            return response.json();
        })
        .then(data => {
            // Mensaje de depuración para verificar los datos obtenidos del servidor
            console.log('Datos obtenidos:', data);
            // Iterar sobre los datos y agregar cada nombre de interés como una opción en el select
            data.forEach(nombreInteres => {
                // Mensaje de depuración para cada interés agregado
                console.log('Agregando interés:', nombreInteres);
                // Crear una nueva opción y agregarla al select
                const option = new Option(nombreInteres, nombreInteres);
                interesesSelect.add(option);
            });
        })
        .catch(error => console.error(error)); // Capturar y mostrar cualquier error que ocurra durante la solicitud

    // Escuchar el evento change en el select de intereses
    interesesSelect.addEventListener('change', (event) => {
        // Reiniciar el campo de texto de intereses seleccionados
        interesesSeleccionadosText.value = '';
        // Obtener todos los intereses seleccionados
        const selectedOptions = Array.from(event.target.selectedOptions);
        // Iterar sobre los intereses seleccionados y agregarlos al campo de texto
        selectedOptions.forEach(option => {
            interesesSeleccionadosText.value += option.value + ', ';
        });
        // Hacer visible el campo de texto de intereses seleccionados
        interesesSeleccionadosText.hidden = false;
    });
});
