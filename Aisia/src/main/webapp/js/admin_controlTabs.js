document.addEventListener("DOMContentLoaded", function() {

    const actividadesNavbar = document.getElementById('actividades-navbar');
    const actividadesTabs = document.querySelectorAll('.actividades-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    actividadesTabs.forEach(tab => {
        if (!tab.classList.contains('actividades-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de actividades
    actividadesNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                actividadesTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }
        }
    });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    const eventosNavbar = document.getElementById('eventos-navbar');
    const eventoTabs = document.querySelectorAll('.evento-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    eventoTabs.forEach(tab => {
        if (!tab.classList.contains('eventos-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de eventos
    eventosNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                eventoTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }
        }
    });

    const usuariosNavbar = document.getElementById('usuarios-navbar');
    const usuariosTabs = document.querySelectorAll('.usuarios-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    usuariosTabs.forEach(tab => {
        if (!tab.classList.contains('usuarios-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de usuarios
    usuariosNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                usuariosTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }
        }
    });

});
