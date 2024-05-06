document.addEventListener('DOMContentLoaded', () => {
    // Obtener el elemento select donde se cargarán los intereses
    const interesesSelect = document.getElementById('intereses');
    const interesesSeleccionadosText = document.getElementById('interesesSeleccionados-text');
    const interesesSeleccionadosContainer = document.getElementById('interesesSeleccionadosContainer');
    
    // Array para almacenar los intereses seleccionados
    let interesesSeleccionados = [];

    // Realizar una solicitud Fetch al servidor para obtener los datos de intereses
    fetch('GestorIntereses?accion=listar')
        .then(response => response.json())
        .then(data => {
            // Iterar sobre los datos y agregar cada nombre de interés como una opción en el select
            data.forEach(nombreInteres => {
                const option = new Option(nombreInteres, nombreInteres);
                interesesSelect.add(option);
            });
        })
        .catch(error => console.error(error)); // Capturar y mostrar cualquier error que ocurra durante la solicitud

});
