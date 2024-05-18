function showEventID(eventID) {
    document.getElementById('eventIDDisplay').textContent = `ID del Evento Seleccionado: ${eventID}`;
}

document.addEventListener('DOMContentLoaded', function () {
    const eventRows = document.querySelectorAll('#Publicados-tabla tr');
    eventRows.forEach(row => {
        row.addEventListener('click', function () {
            const eventID = this.getAttribute('data-event-id');
            showEventID(eventID);
        });
    });
});
