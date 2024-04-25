// Resumen: Este script obtiene los últimos eventos del servlet y los muestra en el aside de la página index.html.

// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener('DOMContentLoaded', function () {
    // Obtener los datos de los últimos eventos del servlet
    fetch('UltimosEventos')
        .then(response => response.json())
        .then(data => {
            const eventosList = document.getElementById('ultimosEventosList');
            // Recorrer cada evento obtenido y crear elementos para mostrarlos en el aside
            data.forEach(evento => {
                const li = document.createElement('li');
                const img = document.createElement('img');
                img.src = `./img/Iconos/${evento.tipoActividad.toLowerCase()}.png`;
                img.alt = `Icono ${evento.tipoActividad}`;
                const a = document.createElement('a');
                a.href = '#';
                a.textContent = evento.nombre;
                li.appendChild(img);
                li.appendChild(a);
                eventosList.appendChild(li);
            });
        })
        .catch(error => console.error('Error al obtener los últimos eventos:', error));
});
