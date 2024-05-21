document.addEventListener('DOMContentLoaded', function () {
    const eventRows = document.querySelectorAll('#Publicados-tabla tr');
    eventRows.forEach(row => {
        row.addEventListener('click', function () {
            const eventID = this.cells[0].textContent; // Obtener el contenido de la primera celda (ID del evento)
            const eventIDDisplay = document.getElementById('eventIDDisplay');
            if (eventIDDisplay) {
                eventIDDisplay.textContent = `ID del Evento Seleccionado: ${eventID}`;
            }
        });
    });
});
