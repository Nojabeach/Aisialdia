document.addEventListener("DOMContentLoaded", function() {
    const eventosNavbar = document.getElementById('eventos-navbar');
    const eventoTabs = document.querySelectorAll('.evento-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    eventoTabs.forEach(tab => {
        if (!tab.classList.contains('eventos-rechazados')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación
    eventosNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        const targetTabId = e.target.getAttribute('href').substring(1);

        // Mostrar la pestaña seleccionada y ocultar las demás
        eventoTabs.forEach(tab => {
            if (tab.id === targetTabId) {
                tab.style.display = 'block';
            } else {
                tab.style.display = 'none';
            }
        });
    });
});
